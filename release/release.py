import os
import sys
import requests
import argparse
import subprocess
from datetime import datetime
from os.path import abspath, join, expanduser, basename


GREEN = '\033[92m'
RED = '\033[91m'
RESET = '\033[0m'
GITLAB_URL = os.environ.get("CI_API_V4_URL", "https://gitlab.com/api/v4")


def publish_package_to_gitlab(version: str, repo: str):
    project_id = os.environ.get("CI_PROJECT_ID", 68602892)
    release_files = {
        "appian-selenium-api": abspath(expanduser(join(repo, f'appian-selenium-api/build/RELEASE-FILES/appian-selenium-api.jar'))),
        "cucumber-for-appian": abspath(expanduser(join(repo, f'cucumber-for-appian/build/RELEASE-FILES/cucumber-for-appian.zip'))),
        "fitnesse-for-appian": abspath(expanduser(join(repo, f'fitnesse-for-appian/build/RELEASE-FILES/fitnesse-for-appian.zip')))
    }
    print("Uploading package to GitLab...")
    gitlab_token = os.environ.get("CI_JOB_TOKEN")
    headers = {'Authorization': f"Bearer {gitlab_token}"}

    for release_file in release_files:
        filename = basename(release_files[release_file])

        with open(release_files[release_file], "rb") as f:
            resp = requests.put(
                f"{GITLAB_URL}/projects/{project_id}/packages/generic/FCS/{version}/{filename}",
                headers=headers, data=f)
            resp.raise_for_status()

    print(f"{GREEN}[SUCCESS] Uploaded release to GitLab package registry {RESET}")


def _run_gradle_release_command(command_name, repo):
    try:
        subprocess.check_call(['./gradlew', command_name], cwd=repo)
        return True, ""
    except Exception as e:
        return False, str(e)


def release(version: str, repo: str, publish: bool):
    print(f"Releasing FCS {version}")
    release_functions = {
        'selenium-api': lambda: _run_gradle_release_command("releaseAppianSeleniumAPI", repo),
        'fitnesse': lambda: _run_gradle_release_command("releaseFitNesseForAppian", repo),
        'cucumber': lambda: _run_gradle_release_command("releaseCucumberForAppian", repo)
    }
    all_success = True
    for module, release_func in release_functions.items():
        success, error_message = release_func()
        all_success = all_success and success
        if success:
            print(f"{GREEN}[SUCCESS] {module}{RESET}")
        else:
            print(f"{RED}[FAILED] {module}{RESET}")
            print(error_message)

    if all_success and publish:
        publish_package_to_gitlab(version, repo)
    return all_success


def get_version(repo: str):
    with open(os.path.join(repo, "shared", "gradle", "common_gradle.properties")) as f:
        lines =f.readlines()
    for l in lines:
        if l.startswith("version"):
            base_version = l.split("=")[-1].strip()
            return f"{base_version}.{datetime.today().timetuple().tm_yday}"

    raise Exception("Unable to get project version")


def run():
    parser = argparse.ArgumentParser(description='A simple release tool.')
    parser.add_argument('--publish', action='store_true', help="Indicates if the release should be published to GitLab")
    parser.add_argument('--repo', help='Path to Fitness repo root', default=".")
    args = parser.parse_args()

    repo = abspath(args.repo)
    version = get_version(repo)
    status = release(version, repo, args.publish)
    if not status:
        sys.exit(100)  # 100 is just a non zero exit code to mark the script failed for CI
    else:
        print("Release complete")
        with open("variables.env", "w") as f:
            f.write(f"PACKAGE_VERSION={version}")


if __name__ == "__main__":
    run()

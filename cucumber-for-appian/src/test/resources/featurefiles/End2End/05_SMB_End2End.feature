@End2End
Feature: 05 SMB End2End feature

  Background: Setup background and environment
    Given I setup browser
    Then I setup appian URL to "appian.active.url"
    And I setup appian version
    And I setup appian locale
    And I set screenshot path to "screenshot.path"
    And I set take error screenshots to "screenshot.boolean"
    And I set stop on error to "screenshot.stop.on.error"

  Scenario: TC001_Trust_Verify Introducer is able to Request a quote and Submit the application for Used Asset and Dealer Pricing with Finance Lease without Services for Construction & Mining
    Given I setup environment and login with role "introducer"
    Given I load test data for "TC001" from "05_SMB_End2End"
    Then I click on button "Request A Quote"
    Then I populate field "Search Journeys" with excel "excel:ABN"
    Then I click on button "SEARCH[2]"
    Then I select grid "[2]" row "[1]"
    Then I click on button "Next"
    Then I click on element with text from excel "excel:What type of asset are you seeking to finance?"
    Then I click on element with text from excel "excel:Do you have a specific construction & mining / yellow good in mind?"
    Then I click on element with text from excel "excel:Is the construction & mining / yellow good new?"
    Then I click on element with text from excel "excel:Is your quote from a Dealer?"
    Then I click on card direct from excel "excel:What product suits you best?"
    Then I populate field "Asset Category[3]" with excel "excel:Vehicle or Asset Category"
    Then I populate field "Asset Description" with excel "excel:Vehicle or Asset Description"
    Then I populate field "Total Asset Cost (incl. GST)" with excel "excel:Total Asset Cost (incl. GST)"
    Then I populating field "Total GST" with excel "excel:Total GST"
    Then I populate field "Dealer Origination Fee (excl. GST)" with excel "excel:Dealer Origination Fee (excl. GST)"
    Then I populate field "Brokerage % (excl. GST)" with excel "excel:Brokerage % (excl. GST)"
    Then I click on radio option from excel "excel:Payment Method"
    Then I click on term card from excel "excel:Select a Term"
    Then I click on button "Next"
    Then I verify text "Indicative Quote Summary" is present
    Then I verify text "Finance Lease without Services" is present
    Then I verify text "Your Construction & Mining / Yellow Good" is present
    Then I verify text "Construction & Mining / Yellow Good Description" is present
    Then I verify text "Total Cost of Lease" is present
    Then I verify text "Total Asset Cost (incl GST)" is present
    Then I click on button "Complete Quote"
    Then I click on button "Proceed Quote"
    Then I click on element with text from excel "excel:Application Type"
    Then I click on button "Next"
#   Applicant Company Details tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "Company Website" contains excel "excel:Company Website"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I populate field "Business Background" with excel "excel:Business Background"
    Then I populate field "Asset Purpose" with excel "excel:Asset Purpose"
    Then I click on button "Next"
#    Financial tab Inputs and data verification
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
    Then I click on button "Next"
#    Owner Individuals tab Inputs and data verification
    Then I verify text "Please identify any individuals who may be a Director, Shareholder, Beneficial Owner, and/or Guarantor to your company" is present
    Then I verify text "Applications without a Director's Guarantee will require further assessment, you will be required to provide 3 years worth of financial statements." is present
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I click on button "Next"
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Gender" contains excel "excel:Gender"
    Then I verify field "Date Of Birth" contains excel "excel:Date Of Birth"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Phone Number" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Licence Number" contains excel "excel:Drivers Licence Number"
    Then I click on button "Next"
    Then I verify field "ABN" contains "66004759611"
    Then I verify field "ACN" contains "004759611"
    Then I verify field "Company Name" contains "HONDA AUSTRALIA PTY. LTD."
    Then I click on button "Next"
    Then I verify text "Please identify any additional trusts who may be a Shareholder and/or Guarantor to your company" is present
    Then I verify field "ABN" contains "45004189708"
    Then I verify field "Trust Name" contains "COLES SUPERMARKETS AUSTRALIA PTY LTD"
    Then I click on button "Next"
#   Summary tab Inputs and data verification
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Company Website" contains excel "excel:Company Website"
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
    Then I verify text "Applications without a Director's Guarantee will require further assessment, you will be required to provide 3 years worth of financial statements." is present
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I click on checkbox option "[1]"
    Then I click on checkbox option "[2]"
    Then I click on checkbox option "[3]"
    Then I click on checkbox option "[4]"
    Then I click on button "Submit"
    Then I wait for "2" seconds
    Then I click on element with text "BEGIN[2]"
    Then I click on element with text "Send SMS"
    Then I verify text "The link sent via SMS will be active for 48 hours." is present
    Then I populates field "File Upload[20]" with excel "excel:Financial Statements"
    Then I wait for "2" seconds
    Then I populates field "File Upload[26]" with excel "excel:Privacy Consent Form"
    Then I click on button "Submit"
    Then I wait for "2" seconds
    Then I get grid "" column "Application Number" row "1" value and store in excel "excel:Application Number"
    Then I get grid "" column "Quote Number" row "1" value and store in excel "excel:Quote Number"
    Then I get grid "" column "Client (Applicant)" row "1" value and store in excel "excel:Client or Applicant"
    Then I get grid "" column "Application Status" row "1" value and store in excel "excel:Application Status"
    Then I get grid "" column "Date Created" row "1" value and store in excel "excel:Date Created"
    Then I get grid "" column "Last Updated User" row "1" value and store in excel "excel:Last Updated User"
    Then I get grid "" column "Actions" row "1" value and store in excel "excel:Last Updated User"
    Then I wait for "40" seconds
    Then I refresh
    Then I wait for "30" seconds
    Then I refresh
    Then I verify grid "" column "Application Status" row "[1]" contains "Submitted"
    Then I click on button "View Application"
    Then I verify text "Applicant Company Details" is present
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Company Website" contains excel "excel:Company Website"
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
    Then I verify text "Applications without a Director's Guarantee will require further assessment, you will be required to provide 3 years worth of financial statements." is present
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"


  Scenario: TC002_Trust_Verify creditReviewer is able to review and Allocate the Application for Trust
    Given I setup environment and login with role "creditReviewer"
    Given I load test data for "TC002" from "05_SMB_End2End"
    When I click on record from excel "excel:Application Number"
#  Applicant Company Details verification
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "GST Registration Date" contains excel "excel:GST Registration Date"
    Then I verify field "Company Website" contains excel "excel:Company Website"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I verify field "Business Background" contains excel "excel:Business Background"
    Then I verify field "Asset Purpose" contains excel "excel:Asset Purpose"
#  Financial Details verification
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
#  Owners - Individuals Details verification
    Then I verify text "Applications without a Director's Guarantee will require further assessment, you will be required to provide 3 years worth of financial statements." is present
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Roles" contains excel "excel:Roles"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Gender" contains excel "excel:Gender"
    Then I verify field "Residential Address" contains excel "excel:Residential Address"
    Then I verify field "Phone Number[2]" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Licence Number" contains excel "excel:Drivers Licence Number"
    #  Company Information 1 Details verification
    Then I verify text "Company 1" is present
    Then I verify field "ABN[3]" contains "66004759611"
    Then I verify field "ACN[2]" contains "004759611"
    Then I verify field "Company Name[2]" contains "HONDA AUSTRALIA PTY. LTD."
    Then I verify field "Roles[2]" contains "Shareholder"
    #  Owners - Trusts 1 Details verification
    Then I verify text "Trust 1" is present
    Then I verify field "ABN[4]" contains "45004189708"
    Then I verify field "Trust Name" contains "COLES SUPERMARKETS AUSTRALIA PTY LTD"
    Then I verify field "Roles[3]" contains "Guarantor"
#  Right side pane Details verification
    Then I verify text "Overall Decision" is present
    Then I verify text "Owner" is present
    Then I verify text "Funding Value" is present
    Then I verify text "Monthly Repayment" is present
    Then I verify text "Customer Tier" is present
    Then I verify text "Application Criteria" is present
    Then I verify text "Full-Doc" is present
    #   Verifying content on 'Credit Rules'
    Then I click on record view "Credit Rules"
    Then I verify grid "" column "Name" row "[1]" contains "ABN Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "ABN age is older than 1 year"
    Then I verify grid "" column "Result" row "[1]" contains "Approved"
    Then I verify grid "" column "Name" row "[2]" contains "Max Loan Amount"
    Then I verify grid "" column "Rule Description" row "[2]" contains "Total Loan Amount is less than $200,000"
    Then I click on button "Individual Guarantor - John Wickner"
    Then I wait for "2" seconds
    Then I verify grid "" column "Name" row "[1]" contains "Credit File Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "Credit file age is equal to or greater than 12 months"
    Then I verify grid "" column "Result" row "[1]" contains "Referred"
    Then I verify grid "" column "Name" row "[2]" contains "Overall Decision"
    Then I verify grid "" column "Result" row "[2]" contains "Referred"
    Then I click on record view "Identity Verification - Applicant"
#    Verifying content on 'Identity Verification - Applicant'
    Then I verify text "This application has not initiated their Identity Verification" is present
    Then I click on record view "Identity Verification - Proprietors"
    Then I verify text "No additional individuals have listed on this application have initiated their Identity Verification" is present
#    Verifying content on 'Identity Verification - Proprietors'
    Then I click on record view "Credit Report - Applicant"
#    Verifying content on 'Credit Report - Applicant'
    Then I verify text "No Equifax Credit Reports to Display for this application." is present
    Then I click on record view "Beneficial Owner Report - Applicant"
    Then I click on record view "Credit Report - Guarantors"
    Then I verify grid "" column "Name of the Guarantor" row "[1]" contains "John Wickner"
    Then I verify grid "" column "Credit Report Name(s)" row "[1]" contains "Equifax Commercial Individual Report"
    Then I verify grid "" column "Status" row "[1]" contains "Success"
    Then I click on record view "Document Extraction"
    Then I verify text "No items available" is present
    Then I click on record view "Documents"
#    Verifying content on 'Documents'
    Then I verify text "Uploaded Documents" is present
    Then I verify grid "" column "Document Name" row "[1]" contains "Financial_Statement_Document.pdf"
    Then I verify grid "" column "Document Type" row "[1]" contains "Financial Statements"
    Then I verify grid "" column "Uploaded By" row "[1]" contains "Automation Introducer"
    Then I verify grid "" column "Document Name" row "[2]" contains "Privacy_Consent_Form.pdf"
    Then I verify grid "" column "Document Type" row "[2]" contains "Privacy Consent Form"
    Then I verify grid "" column "Uploaded By" row "[2]" contains "Automation Introducer"
    Then I click on record view "Communications"
    Then I verify text "No items available" is present
    Then I click on record view "Event History"
#    Verifying content on 'Event History'
    Then I verify text "Created Application" is present
    Then I verify text "Signed Privacy Statement" is present
    Then I verify text "Updated Application" is present
    Then I click on record view "Quote Details"
#    Verifying content on 'Quote Details'
    Then I verify text "Finance Lease without Services" is present
    Then I verify text "Dump Trucks" is present
    Then I verify field "Total Asset Cost" contains excel "excel:Total Asset Cost (incl. GST)"
    Then I verify field "GST" contains excel "excel:Total GST"
    Then I verify field "Total Cost of Lease" contains excel "excel:Total Cost of Lease"
    Then I verify field "Monthly Rental" contains excel "excel:Monthly Rental"
    Then I verify field "Specific Asset" contains excel "excel:Specific Asset"
    Then I verify field "New Asset" contains excel "excel:New Asset"
    Then I verify field "Payment Method" contains excel "excel:Payment Method"
    Then I verify field "Pricing Type" contains excel "excel:Pricing Type"
    Then I verify field "Asset Type" contains excel "excel:Asset Type"
    Then I verify field "Monthly GST" contains excel "excel:Monthly GST"
    Then I verify field "Total GST" contains excel "excel:Total GST"
    Then I verify field "Residual Percentage" contains excel "excel:Residual Percentage"
    Then I verify field "Asset Category[2]" contains excel "excel:Vehicle or Asset Category"
    Then I verify field "Business" contains excel "excel:Company Name"
    Then I verify field "Introducer" contains "Dandenong Mits Pty Ltd"
    Then I verify field "Status" contains excel "excel:Status"
    Then I verify field "Document" contains excel "excel:Document"
    Then I click on record view "Summary"
    Then I click on button "Allocate Application"
    Then I wait for "3" seconds
    Then I verify text "Allocate Credit Application" is present
    Then I populate field "Credit Officer" with excel "excel:Credit Officer"
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Referred" is present
    Then I verify text "Automation Credit Manager" is present


  Scenario: TC003_Trust_Verify creditManager is able to overwrite credit decision for Trust
    Given I setup environment and login with role "creditManager"
    Given I load test data for "TC003" from "05_SMB_End2End"
    When I click on record from excel "excel:Application Number"
#  Applicant Company Details verification
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "GST Registration Date" contains excel "excel:GST Registration Date"
    Then I verify field "Company Website" contains excel "excel:Company Website"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I verify field "Business Background" contains excel "excel:Business Background"
    Then I verify field "Asset Purpose" contains excel "excel:Asset Purpose"
#  Financial Details verification
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
#  Owners - Individuals Details verification
    Then I verify text "Applications without a Director's Guarantee will require further assessment, you will be required to provide 3 years worth of financial statements." is present
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Roles" contains excel "excel:Roles"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Gender" contains excel "excel:Gender"
    Then I verify field "Residential Address" contains excel "excel:Residential Address"
    Then I verify field "Phone Number[2]" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Licence Number" contains excel "excel:Drivers Licence Number"
    #  Company Information 1 Details verification
    Then I verify text "Company 1" is present
    Then I verify field "ABN[3]" contains "66004759611"
    Then I verify field "ACN[2]" contains "004759611"
    Then I verify field "Company Name[2]" contains "HONDA AUSTRALIA PTY. LTD."
    Then I verify field "Roles[2]" contains "Shareholder"
    #  Owners - Trusts 1 Details verification
    Then I verify text "Trust 1" is present
    Then I verify field "ABN[4]" contains "45004189708"
    Then I verify field "Trust Name" contains "COLES SUPERMARKETS AUSTRALIA PTY LTD"
    Then I verify field "Roles[3]" contains "Guarantor"
#  Right side pane Details verification
    Then I verify text "Overall Decision" is present
    Then I verify text "Owner" is present
    Then I verify text "Funding Value" is present
    Then I verify text "Monthly Repayment" is present
    Then I verify text "Customer Tier" is present
    Then I verify text "Application Criteria" is present
    Then I verify text "Full-Doc" is present
    #   Verifying content on 'Credit Rules'
    Then I click on record view "Credit Rules"
    Then I verify grid "" column "Name" row "[1]" contains "ABN Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "ABN age is older than 1 year"
    Then I verify grid "" column "Result" row "[1]" contains "Approved"
    Then I verify grid "" column "Name" row "[2]" contains "Max Loan Amount"
    Then I verify grid "" column "Rule Description" row "[2]" contains "Total Loan Amount is less than $200,000"
    Then I click on button "Individual Guarantor - John Wickner"
    Then I wait for "2" seconds
    Then I verify grid "" column "Name" row "[1]" contains "Credit File Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "Credit file age is equal to or greater than 12 months"
    Then I verify grid "" column "Result" row "[1]" contains "Referred"
    Then I verify grid "" column "Name" row "[2]" contains "Overall Decision"
    Then I verify grid "" column "Result" row "[2]" contains "Referred"
    Then I click on record view "Identity Verification - Applicant"
#    Verifying content on 'Identity Verification - Applicant'
    Then I verify text "This application has not initiated their Identity Verification" is present
    Then I click on record view "Identity Verification - Proprietors"
    Then I verify text "No additional individuals have listed on this application have initiated their Identity Verification" is present
#    Verifying content on 'Identity Verification - Proprietors'
    Then I click on record view "Credit Report - Applicant"
#    Verifying content on 'Credit Report - Applicant'
    Then I verify text "No Equifax Credit Reports to Display for this application." is present
    Then I click on record view "Beneficial Owner Report - Applicant"
    Then I click on record view "Credit Report - Guarantors"
    Then I verify grid "" column "Name of the Guarantor" row "[1]" contains "John Wickner"
    Then I verify grid "" column "Credit Report Name(s)" row "[1]" contains "Equifax Commercial Individual Report"
    Then I verify grid "" column "Status" row "[1]" contains "Success"
    Then I click on record view "Document Extraction"
    Then I verify text "No items available" is present
    Then I click on record view "Documents"
#    Verifying content on 'Documents'
    Then I verify text "Uploaded Documents" is present
    Then I verify grid "" column "Document Name" row "[1]" contains "Financial_Statement_Document.pdf"
    Then I verify grid "" column "Document Type" row "[1]" contains "Financial Statements"
    Then I verify grid "" column "Uploaded By" row "[1]" contains "Automation Introducer"
    Then I verify grid "" column "Document Name" row "[2]" contains "Privacy_Consent_Form.pdf"
    Then I verify grid "" column "Document Type" row "[2]" contains "Privacy Consent Form"
    Then I verify grid "" column "Uploaded By" row "[2]" contains "Automation Introducer"
    Then I click on record view "Communications"
    Then I verify text "No items available" is present
    Then I click on record view "Event History"
#    Verifying content on 'Event History'
    Then I verify text "Created Application" is present
    Then I verify text "Signed Privacy Statement" is present
    Then I verify text "Updated Application" is present
    Then I click on record view "Quote Details"
#    Verifying content on 'Quote Details'
    Then I verify text "Finance Lease without Services" is present
    Then I verify text "Dump Trucks" is present
    Then I verify field "Total Asset Cost" contains excel "excel:Total Asset Cost (incl. GST)"
    Then I verify field "GST" contains excel "excel:Total GST"
    Then I verify field "Total Cost of Lease" contains excel "excel:Total Cost of Lease"
    Then I verify field "Monthly Rental" contains excel "excel:Monthly Rental"
    Then I verify field "Specific Asset" contains excel "excel:Specific Asset"
    Then I verify field "New Asset" contains excel "excel:New Asset"
    Then I verify field "Payment Method" contains excel "excel:Payment Method"
    Then I verify field "Pricing Type" contains excel "excel:Pricing Type"
    Then I verify field "Asset Type" contains excel "excel:Asset Type"
    Then I verify field "Monthly GST" contains excel "excel:Monthly GST"
    Then I verify field "Total GST" contains excel "excel:Total GST"
    Then I verify field "Residual Percentage" contains excel "excel:Residual Percentage"
    Then I verify field "Asset Category[2]" contains excel "excel:Vehicle or Asset Category"
    Then I verify field "Business" contains excel "excel:Company Name"
    Then I verify field "Introducer" contains "Dandenong Mits Pty Ltd"
    Then I verify field "Status" contains excel "excel:Status"
    Then I verify field "Document" contains excel "excel:Document"
    Then I click on record view "Summary"
    Then I verify text "Referred" is present
    Then I click on button "Update Credit Decision"
    Then I populate field "Decision" with excel "excel:Decision"
    Then I populate field "Allocated Officer for Final Decision" with excel "excel:Credit Officer"
    Then I populate field "Reason for Decline" with excel "excel:Reason for Decline"
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Recommend Decline" is present
    Then I verify text "Automation Credit Approver" is present

  Scenario: TC004_Trust_Verify creditApprover is able to update credit decision and Cancel the Application for Trust
    Given I setup environment and login with role "creditApprover"
    Given I load test data for "TC004" from "05_SMB_End2End"
    When I click on record from excel "excel:Application Number"
#  Applicant Company Details verification
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "GST Registration Date" contains excel "excel:GST Registration Date"
    Then I verify field "Company Website" contains excel "excel:Company Website"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I verify field "Business Background" contains excel "excel:Business Background"
    Then I verify field "Asset Purpose" contains excel "excel:Asset Purpose"
#  Financial Details verification
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
#  Owners - Individuals Details verification
    Then I verify text "Applications without a Director's Guarantee will require further assessment, you will be required to provide 3 years worth of financial statements." is present
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Roles" contains excel "excel:Roles"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Gender" contains excel "excel:Gender"
    Then I verify field "Residential Address" contains excel "excel:Residential Address"
    Then I verify field "Phone Number[2]" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Licence Number" contains excel "excel:Drivers Licence Number"
    #  Company Information 1 Details verification
    Then I verify text "Company 1" is present
    Then I verify field "ABN[3]" contains "66004759611"
    Then I verify field "ACN[2]" contains "004759611"
    Then I verify field "Company Name[2]" contains "HONDA AUSTRALIA PTY. LTD."
    Then I verify field "Roles[2]" contains "Shareholder"
    #  Owners - Trusts 1 Details verification
    Then I verify text "Trust 1" is present
    Then I verify field "ABN[4]" contains "45004189708"
    Then I verify field "Trust Name" contains "COLES SUPERMARKETS AUSTRALIA PTY LTD"
    Then I verify field "Roles[3]" contains "Guarantor"
#  Right side pane Details verification
    Then I verify text "Overall Decision" is present
    Then I verify text "Owner" is present
    Then I verify text "Funding Value" is present
    Then I verify text "Monthly Repayment" is present
    Then I verify text "Customer Tier" is present
    Then I verify text "Application Criteria" is present
    Then I verify text "Full-Doc" is present
    #   Verifying content on 'Credit Rules'
    Then I click on record view "Credit Rules"
    Then I verify grid "" column "Name" row "[1]" contains "ABN Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "ABN age is older than 1 year"
    Then I verify grid "" column "Result" row "[1]" contains "Approved"
    Then I verify grid "" column "Name" row "[2]" contains "Max Loan Amount"
    Then I verify grid "" column "Rule Description" row "[2]" contains "Total Loan Amount is less than $200,000"
    Then I click on button "Individual Guarantor - John Wickner"
    Then I wait for "2" seconds
    Then I verify grid "" column "Name" row "[1]" contains "Credit File Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "Credit file age is equal to or greater than 12 months"
    Then I verify grid "" column "Result" row "[1]" contains "Referred"
    Then I verify grid "" column "Name" row "[2]" contains "Overall Decision"
    Then I verify grid "" column "Result" row "[2]" contains "Referred"
    Then I click on record view "Identity Verification - Applicant"
#    Verifying content on 'Identity Verification - Applicant'
    Then I verify text "This application has not initiated their Identity Verification" is present
    Then I click on record view "Identity Verification - Proprietors"
    Then I verify text "No additional individuals have listed on this application have initiated their Identity Verification" is present
#    Verifying content on 'Identity Verification - Proprietors'
    Then I click on record view "Credit Report - Applicant"
#    Verifying content on 'Credit Report - Applicant'
    Then I verify text "No Equifax Credit Reports to Display for this application." is present
    Then I click on record view "Beneficial Owner Report - Applicant"
    Then I click on record view "Credit Report - Guarantors"
    Then I verify grid "" column "Name of the Guarantor" row "[1]" contains "John Wickner"
    Then I verify grid "" column "Credit Report Name(s)" row "[1]" contains "Equifax Commercial Individual Report"
    Then I verify grid "" column "Status" row "[1]" contains "Success"
    Then I click on record view "Document Extraction"
    Then I verify text "No items available" is present
    Then I click on record view "Documents"
#    Verifying content on 'Documents'
    Then I verify text "Uploaded Documents" is present
    Then I verify grid "" column "Document Name" row "[1]" contains "Financial_Statement_Document.pdf"
    Then I verify grid "" column "Document Type" row "[1]" contains "Financial Statements"
    Then I verify grid "" column "Uploaded By" row "[1]" contains "Automation Introducer"
    Then I verify grid "" column "Document Name" row "[2]" contains "Privacy_Consent_Form.pdf"
    Then I verify grid "" column "Document Type" row "[2]" contains "Privacy Consent Form"
    Then I verify grid "" column "Uploaded By" row "[2]" contains "Automation Introducer"
    Then I click on record view "Communications"
    Then I verify text "No items available" is present
    Then I click on record view "Event History"
#    Verifying content on 'Event History'
    Then I verify text "Created Application" is present
    Then I verify text "Signed Privacy Statement" is present
    Then I verify text "Updated Application" is present
    Then I click on record view "Quote Details"
#    Verifying content on 'Quote Details'
    Then I verify text "Finance Lease without Services" is present
    Then I verify text "Dump Trucks" is present
    Then I verify field "Total Asset Cost" contains excel "excel:Total Asset Cost (incl. GST)"
    Then I verify field "GST" contains excel "excel:Total GST"
    Then I verify field "Total Cost of Lease" contains excel "excel:Total Cost of Lease"
    Then I verify field "Monthly Rental" contains excel "excel:Monthly Rental"
    Then I verify field "Specific Asset" contains excel "excel:Specific Asset"
    Then I verify field "New Asset" contains excel "excel:New Asset"
    Then I verify field "Payment Method" contains excel "excel:Payment Method"
    Then I verify field "Pricing Type" contains excel "excel:Pricing Type"
    Then I verify field "Asset Type" contains excel "excel:Asset Type"
    Then I verify field "Monthly GST" contains excel "excel:Monthly GST"
    Then I verify field "Total GST" contains excel "excel:Total GST"
    Then I verify field "Residual Percentage" contains excel "excel:Residual Percentage"
    Then I verify field "Asset Category[2]" contains excel "excel:Vehicle or Asset Category"
    Then I verify field "Business" contains excel "excel:Company Name"
    Then I verify field "Introducer" contains "Dandenong Mits Pty Ltd"
    Then I verify field "Status" contains excel "excel:Status"
    Then I verify field "Document" contains excel "excel:Document"
    Then I click on record view "Summary"
    Then I verify text "Referred" is present
    Then I click on button "Update Credit Decision"
    Then I populate field "Decision" with excel "excel:Decision"
    Then I populate field "Reason for Decline" with excel "excel:Reason for Decline"
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Declined" is present
    Then I verify text "Automation Credit Approver" is present
    Then I wait for "3" seconds
    Then I click on button "Cancel Application"
    Then I populate field "Reason For Cancellation" with "Request by Client"
    Then I click on button "Cancel[2]"
    Then I verify text "Overall Decision" is present
    Then I verify text "Cancelled" is present
    Then I verify text "Cancelled Application" is present
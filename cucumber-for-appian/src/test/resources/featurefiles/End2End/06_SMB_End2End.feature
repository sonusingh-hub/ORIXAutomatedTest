@End2End
Feature: 06 SMB End2End feature

  Background: Setup background and environment
    Given I setup browser
    Then I setup appian URL to "appian.active.url"
    And I setup appian version
    And I setup appian locale

  Scenario: TC001_Verify Introducer is able to Request a quote and Submit the application for New Asset and Dealer Pricing with Chattel Mortgage for Agricultural Equipment
    Given I setup environment and login with role "introducer"
    Given I load test data for "TC001" from "06_SMB_End2End"
    Then I click on button "Request A Quote"
    Then I populate field "Search Journeys" with excel "excel:ABN"
    Then I click on button "SEARCH[2]"
    Then I select grid "[2]" row "[1]"
    Then I click on button "Next"
    Then I click on element with text from excel "excel:What type of asset are you seeking to finance?"
    Then I click on element with text from excel "excel:Do you have a specific agricultural equipment in mind?"
    Then I click on element with text from excel "excel:Is the agricultural equipment new?"
    Then I click on card direct from excel "excel:What product suits you best?"
    Then I populate field "Asset Category[3]" with excel "excel:Vehicle or Asset Category"
    Then I populate field "Asset Description" with excel "excel:Vehicle or Asset Description"
    Then I populate field "Total Asset Cost (incl. GST)" with excel "excel:Total Asset Cost (incl. GST)"
    Then I populating field "Deposit Amount" with excel "excel:Deposit Amount"
    Then I populate field "Dealer Origination Fee (excl. GST)" with excel "excel:Dealer Origination Fee (excl. GST)"
    Then I populate field "Brokerage % (excl. GST)" with excel "excel:Brokerage % (excl. GST)"
    Then I populate field "Balloon %" with excel "excel:Balloon %"
    Then I click on radio option from excel "excel:Payment Method"
    Then I click on term card from excel "excel:Select a Term"
    Then I get field "Asset Category" value and store in excel "excel:Output Vehicle or Asset Category"
    Then I get field "Asset Description" value and store in excel "excel:Output Vehicle or Asset Description"
    Then I click on button "Next"
    Then I populate field "VIN" with excel "excel:VIN"
    Then I populate field "Registration Number" with excel "excel:Registration Number"
    Then I populate field "Engine Number" with excel "excel:Engine Number"
    Then I populate field "Other Comments" with excel "excel:Other Comments"
    Then I click on button "Next"
    Then I verify text "A value is required" is present
    Then I populate field "Quote Document" with excel "excel:Quote Document"
    Then I click on button "Next"
    Then I verify text "Indicative Quote Summary" is present
    Then I verify text "Chattel Mortgage" is present
    Then I verify text "Your Agricultural Equipment" is present
    Then I verify text "Agricultural Equipment Description" is present
    Then I verify text "Total Cost of Loan" is present
    Then I verify text "Net Asset Cost (incl GST)" is present
    Then I click on button "Complete Quote"
    Then I click on button "Proceed Quote"
    Then I click on element with text from excel "excel:Application Type"
    Then I click on button "Next"
#   Business tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Registered Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I populate field "Business Background" with excel "excel:Business Background"
    Then I populate field "Asset Purpose" with excel "excel:Asset Purpose"
    Then I click on button "Next"
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Date Of Birth" contains excel "excel:Date Of Birth"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Gender" contains excel "excel:Gender"
    Then I verify field "Residential Address" contains excel "excel:Residential Address"
    Then I verify field "Phone Number" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Drivers Licence Number" contains excel "excel:Drivers Licence Number"
    Then I click on button "Next"
#   Financial tab Inputs and data verification
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I click on button "Next"
#   Summary tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Registered Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I click on checkbox option "[1]"
    Then I click on checkbox option "[2]"
    Then I click on checkbox option "[3]"
    Then I click on checkbox option "[4]"
    Then I click on button "Submit"
    Then I click on element with text "BEGIN[2]"
    Then I click on element with text "Send SMS"
    Then I verify text "The link sent via SMS will be active for 48 hours." is present
    Then I populates field "File Upload[20]" with excel "excel:Rental Agreement"
    Then I click on button "Submit"
    Then I get grid "" column "Application Number" row "1" value and store in excel "excel:Application Number"
    Then I get grid "" column "Quote Number" row "1" value and store in excel "excel:Quote Number"
    Then I get grid "" column "Client (Applicant)" row "1" value and store in excel "excel:Client or Applicant"
    Then I get grid "" column "Application Status" row "1" value and store in excel "excel:Application Status"
    Then I get grid "" column "Date Created" row "1" value and store in excel "excel:Date Created"
    Then I get grid "" column "Last Updated User" row "1" value and store in excel "excel:Last Updated User"

  Scenario: TC002_Verify creditManager is able to validate the credit rules and manually overwrite credit decision
    Given I setup environment and login with role "creditManager"
    Given I load test data for "TC002" from "06_SMB_End2End"
    When I click on record from excel "excel:Application Number"
#  Business tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Registered Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "GST Registration Date" contains excel "excel:GST Registration Date"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Gender" contains excel "excel:Gender"
    Then I verify field "Residential Address" contains excel "excel:Residential Address"
    Then I verify field "Phone Number" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Drivers Licence Number" contains excel "excel:Drivers Licence Number"
    Then I verify field "Income" contains excel "excel:Income"
    Then I verify field "Expenses" contains excel "excel:Expenses"
    Then I verify field "Income Frequency" contains excel "excel:Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:Expenses Frequency"
    Then I verify text "Overall Decision" is present
    Then I verify text "Owner" is present
    Then I verify text "Funding Value" is present
    Then I verify text "Monthly Repayment" is present
    Then I verify text "Customer Tier" is present
    Then I verify text "Application Criteria" is present
    Then I verify text "Low-Doc" is present
    Then I click on record view "Credit Rules"
#   Verifying content on 'Credit Rules'
    Then I verify text "Credit Rule Details" is present
    Then I verify grid "" column "Name" row "[1]" contains "ABN Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "ABN age is older than 1 year"
    Then I verify grid "" column "Value" row "[1]" contains "6 Years, 9 Months"
    Then I verify grid "" column "Result" row "[1]" contains "Approved"
    Then I verify grid "" column "Name" row "[2]" contains "Max Loan Amount"
    Then I verify grid "" column "Rule Description" row "[2]" contains "Total Loan Amount is less than $200,000"
    Then I verify grid "" column "Value" row "[2]" contains "$61,375.00"
    Then I verify grid "" column "Result" row "[2]" contains "Approved"
    Then I verify grid "" column "Name" row "[3]" contains "Min Applicant Age"
    Then I verify grid "" column "Rule Description" row "[3]" contains "Applicant age is greater than 25 years"
    Then I verify grid "" column "Value" row "[3]" contains "44 Years, 8 Months"
    Then I verify grid "" column "Result" row "[3]" contains "Approved"
    Then I verify grid "" column "Name" row "[4]" contains "Max Applicant Age"
    Then I verify grid "" column "Rule Description" row "[4]" contains "Applicant age is less than 70 years"
    Then I verify grid "" column "Value" row "[4]" contains "44 Years, 8 Months"
    Then I verify grid "" column "Result" row "[4]" contains "Approved"
    Then I verify grid "" column "Name" row "[5]" contains "Citizenship Status"
    Then I verify grid "" column "Rule Description" row "[5]" contains "Applicant is an Australian citizen or permanent resident"
    Then I verify grid "" column "Value" row "[5]" contains "True"
    Then I verify grid "" column "Result" row "[5]" contains "Approved"
    Then I verify grid "" column "Name" row "[6]" contains "Residential Status"
    Then I verify grid "" column "Rule Description" row "[6]" contains "Applicant does not have a residential status of 'other'"
    Then I verify grid "" column "Value" row "[6]" contains "True"
    Then I verify grid "" column "Result" row "[6]" contains "Approved"
    Then I verify grid "" column "Name" row "[7]" contains "Credit File Age"
    Then I verify grid "" column "Rule Description" row "[7]" contains "Credit file age is equal to or greater than 12 months"
    Then I verify grid "" column "Value" row "[7]" contains "8 Years, 0 Months"
    Then I verify grid "" column "Result" row "[7]" contains "Approved"
    Then I verify grid "" column "Name" row "[8]" contains "Credit File Activity"
    Then I verify grid "" column "Rule Description" row "[8]" contains "Credit file has activity recorded in the past 24 months from date of submission for current credit application"
    Then I verify grid "" column "Value" row "[8]" contains "True"
    Then I verify grid "" column "Result" row "[8]" contains "Approved"
    Then I verify grid "" column "Name" row "[9]" contains "Adverse Credit File"
    Then I verify grid "" column "Rule Description" row "[9]" contains "Applicant has no adverse on their credit file"
    Then I verify grid "" column "Value" row "[9]" contains "True"
    Then I verify grid "" column "Result" row "[9]" contains "Approved"
    Then I verify grid "" column "Name" row "[10]" contains "Lending Enquiries"
    Then I verify grid "" column "Rule Description" row "[10]" contains "Applicant has made more than 5 lending enquiries in the past 30 days and more than 20 in the last 180 days. "
    Then I verify grid "" column "Value" row "[10]" contains "False"
    Then I verify grid "" column "Result" row "[10]" contains "Approved"
    Then I verify grid "" column "Name" row "[11]" contains "Court Writs/Actions"
    Then I verify grid "" column "Rule Description" row "[11]" contains "Applicant has no Court Writ and/or Court Action on their credit file"
    Then I verify grid "" column "Value" row "[11]" contains "True"
    Then I verify grid "" column "Result" row "[11]" contains "Approved"
    Then I verify grid "" column "Name" row "[12]" contains "Credit Score (Tier A)"
    Then I verify grid "" column "Rule Description" row "[12]" contains "Credit Score is greater than 549"
    Then I verify grid "" column "Value" row "[12]" contains "True"
    Then I verify grid "" column "Result" row "[12]" contains "Approved"
    Then I verify grid "" column "Name" row "[13]" contains "Green ID - Identity Verification"
    Then I verify grid "" column "Rule Description" row "[13]" contains "Identity verification pass on GreenID"
    Then I verify grid "" column "Value" row "[13]" contains "False"
    Then I verify grid "" column "Result" row "[13]" contains "Referred"
    Then I verify grid "" column "Name" row "[14]" contains "Green ID - PEP/Sanction check"
    Then I verify grid "" column "Rule Description" row "[14]" contains "No match found on PEP/Sanctions check"
    Then I verify grid "" column "Value" row "[14]" contains "True"
    Then I verify grid "" column "Result" row "[14]" contains "Approved"
    Then I verify grid "" column "Name" row "[15]" contains "ANZSIC Code"
    Then I verify grid "" column "Rule Description" row "[15]" contains "ANZSIC Industry classification does not meet ORIX policy"
    Then I verify grid "" column "Value" row "[15]" contains "False"
    Then I verify grid "" column "Result" row "[15]" contains "Approved"
    Then I verify grid "" column "Name" row "[16]" contains "Application Criteria"
    Then I verify grid "" column "Rule Description" row "[16]" contains "The Application falls under the Low Doc criteria"
    Then I verify grid "" column "Value" row "[16]" contains "True"
    Then I verify grid "" column "Result" row "[16]" contains "Approved"
    Then I verify grid "" column "Name" row "[17]" contains "NZ Citizen"
    Then I verify grid "" column "Rule Description" row "[17]" contains "An Individual with NZ Citizenship has been identified"
    Then I verify grid "" column "Value" row "[17]" contains "False"
    Then I verify grid "" column "Result" row "[17]" contains "Approved"
    Then I verify grid "" column "Name" row "[18]" contains "Overall Decision"
    Then I verify grid "" column "Result" row "[18]" contains "Referred"
    Then I click on record view "Identity Verification - Applicant"
#    Verifying content on 'Identity Verification - Applicant'
    Then I verify text "All Identity Verification Attempts" is present
    Then I verify text "Identity Verification Summary" is present
    Then I verify text "OCR Document" is present
    Then I verify text "Background Checks" is present
    Then I verify text "DVS Checks" is present
    Then I click on record view "Identity Verification - Proprietors"
#    Verifying content on 'Identity Verification - Proprietors'
    Then I click on record view "Credit Report - Applicant"
#    Verifying content on 'Credit Report - Applicant'
    Then I click on button "Re-Run Equifax Check"
    Then I verify text "Equifax Check is in progress." is present
    Then I verify text "Click 'Done' to return to home screen." is present
    Then I click on button "Done"
    Then I click on record view "Beneficial Owner Report - Applicant"
    Then I click on record view "Credit Report - Guarantors"
    Then I click on record view "Document Extraction"
    Then I click on record view "Documents"
#    Verifying content on 'Documents'
    Then I verify text "Uploaded Documents" is present
    Then I verify grid "" column "Document Name" row "[1]" contains "Rental_Agreement_Document.pdf"
    Then I verify grid "" column "Document Type" row "[1]" contains "Rental Agreement"
    Then I verify grid "" column "Uploaded By" row "[1]" contains "Automation Introducer"
    Then I click on record view "Communications"
    Then I click on record view "Event History"
#    Verifying content on 'Event History'
    Then I verify text "Created Application" is present
    Then I verify text "Signed Privacy Statement" is present
    Then I verify text "Updated Application" is present
    Then I click on record view "Quote Details"
#    Verifying content on 'Quote Details'
    Then I verify text "Chattel Mortgage" is present
    Then I verify text "Planting equipment" is present
    Then I verify field "Total Asset Cost" contains excel "excel:Total Asset Cost (incl. GST)"
    Then I verify field "Balloon Payment" contains excel "excel:Balloon Payment"
    Then I verify field "Total Cost of Loan" contains excel "excel:Total Cost of Loan"
    Then I verify field "Monthly Payment" contains excel "excel:Monthly Payment"
    Then I verify field "Specific Asset" contains excel "excel:Specific Asset"
    Then I verify field "New Asset" contains excel "excel:New Asset"
    Then I verify field "Payment Method" contains excel "excel:Payment Method"
    Then I verify field "Pricing Type" contains excel "excel:Pricing Type"
    Then I verify field "Asset Type" contains excel "excel:Asset Type"
    Then I verify field "Monthly GST" contains excel "excel:Monthly GST"
    Then I verify field "Total GST" contains excel "excel:Total GST"
    Then I verify field "Deposit Percentage" contains excel "excel:Deposit Percentage"
    Then I verify field "Balloon Percentage" contains excel "excel:Balloon %"
    Then I verify field "Status" contains excel "excel:Status"
    Then I verify field "Document" contains excel "excel:Document"
    Then I verify field "Dealer Quote Document" contains excel "excel:Dealer Quote Document"
    Then I click on record view "Summary"
    Then I verify text "Referred" is present
    Then I verify text "Green ID - Identity Verification" is present
    Then I click on button "Take Ownership"
    Then I click on button "Done"
    Then I verify text "Automation Credit Manager" is present
    Then I click on button "Update Credit Decision"
    Then I populate field "Decision" with excel "excel:Decision"
    Then I populate field "Conditions" with excel "excel:Conditions of Approval"
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Conditional Approval" is present
    Then I verify text "Copy of residential tenancy agreement to be provided to confirm current residential address." is present
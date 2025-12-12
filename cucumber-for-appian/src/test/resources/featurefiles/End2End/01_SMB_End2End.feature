@End2End
Feature: 01 SMB End2End feature

  Background: Setup background and environment
    Given I setup browser
    Then I setup appian URL to "appian.active.url"
    And I setup appian version
    And I setup appian locale

  Scenario: TC001_Verify an existing ORIX Introducer is able to create a quote and submit an application
    Given I setup environment and login with role "introducer"
    Then I click on button "Request a Quote"
    Then I populate field "Search Journeys" with "68173780899"
    Then I click on button "Search[2]"
    Then I select grid "[2]" row "[1]"
    Then I click on button "Next"
    Then I click on text "Passenger or Light Commercial Vehicle"
    Then I click on text "Yes"
    Then I click on text "Yes, it is for a new asset"
    Then I click on text "No, I have a quote from a dealer"
    Then I click on card direct "Chattel Mortgage"
    Then I populate field "Vehicle Category" with "Passenger cars including electric vehicles"
    Then I populate field "Vehicle Description" with "Toyota Hatch"
    Then I populate field "Total Asset Cost (incl. GST)" with "$56,000.00"
    Then I wait for "3" seconds
    Then I populating field "Deposit Amount" with "$1,500"
    Then I populate field "Dealer Origination Fee (excl. GST)" with "$1,000"
    Then I populate field "Brokerage % (excl. GST)" with "5.00%"
    Then I populate field "Balloon %" with "30.00%"
    Then I click on radio option "Monthly in Advance"
    Then I click on card direct "Fuel Card"
    Then I click on term card "36 months"
    Then I click on button "Next"
    Then I populate field "Make" with "TOYOTA"
    Then I populate field "Model" with "BZ4X"
    Then I populate field "Sub Model" with "71kWh AWD"
    Then I click on button "SEARCH[2]"
    Then I click on button "SELECT VEHICLE"
    Then I populate field "VIN" with "TOYOTAVIN12345678"
    Then I populate field "Registration Number" with "TOYOTAREG123456"
    Then I populate field "Engine Number" with "TOYOTAENG123456"
    Then I populate field "Other Comments" with "Comments: TOYOTA Passenger cars including electric vehicles"
    Then I click on button "Next"
    Then I verify text "A value is required" is present
    Then I populate field "Quote Document" with "D:\ORIX_AUS\UploadDocs\Quote_Document.pdf"
    Then I click on button "Next"
    Then I verify text "Indicative Quote Summary" is present
    Then I verify text "Chattel Mortgage" is present
    Then I verify text "Your Passenger or Light Commercial Vehicle" is present
    Then I verify text "Passenger or Light Commercial Vehicle Description" is present
    Then I verify text "Total Cost of Loan" is present
    Then I verify text "Net Asset Cost (incl GST)" is present
    Then I click on button "Complete Quote"
    Then I click on button "Proceed Quote"
    Then I click on text "New Application[2]"
    Then I click on button "Next"
#   Business tab Inputs and data verification
    Then I verify field "ABN" contains "68173780899"
    Then I verify field "Registered Business Name" contains "AGARWAL, ANGEL"
    Then I verify field "Business Address" contains "10 St Barnabas Bvd, Quinns Rocks WA 6030"
    Then I verify field "Industry ANZSIC Code" contains "A AGRICULTURE, FORESTRY AND FISHING"
    Then I verify field "Industry Classification" contains "013 Fruit and Tree Nut Growing"
    Then I populate field "Business Background" with "Agriculture Background"
    Then I populate field "Asset Purpose" with "Agriculture Advertisement"
    Then I click on button "Next"
    Then I verify field "First Name" contains "darren"
    Then I verify field "Last Name" contains "davies"
    Then I verify field "Citizenship Status" contains "Australian Citizen"
    Then I verify field "Date Of Birth" contains "27/05/1981"
    Then I verify field "Residential Status" contains "Renting"
    Then I verify field "Gender" contains "Male"
    Then I verify field "Residential Address" contains "34 Rochester Dr, Mindarie WA 6030"
    Then I verify field "Phone Number" contains "+61400000000"
    Then I verify field "Email" contains "chandu.nagu+60@gmail.com"
    Then I verify field "License Type" contains "Full Licence"
    Then I verify field "License Class" contains "Heavy Rigid (HR)"
    Then I verify field "License State" contains "NSW"
    Then I verify field "Drivers Licence Number" contains "88310877"
    Then I click on button "Next"
#   Financial tab Inputs and data verification
    Then I verify field "Income" contains "$30,000.00"
    Then I verify field "Expenses" contains "$2,000.00"
    Then I click on button "Next"
#   Summary tab Inputs and data verification
    Then I verify field "ABN" contains "68173780899"
    Then I verify field "Registered Business Name" contains "AGARWAL, ANGEL"
    Then I verify field "First Name" contains "darren"
    Then I verify field "Last Name" contains "davies"
    Then I verify field "Income" contains "$30,000.00"
    Then I verify field "Expenses" contains "$2,000.00"
    Then I click on checkbox option "[1]"
    Then I click on checkbox option "[2]"
    Then I click on checkbox option "[3]"
    Then I click on checkbox option "[4]"
    Then I click on button "Submit"
    Then I click on text "BEGIN[2]"
    Then I click on text "Send SMS"
    Then I verify text "The link sent via SMS will be active for 48 hours." is present
    Then I populates field "File Upload" with "D:\ORIX_AUS\UploadDocs\Rental_Agreement_Document.pdf"
    Then I click on button "Submit"

  Scenario: TC002_Verify if user is able to validate the credit rules and manually overwrite credit decision for Sole Trader
    Given I setup environment and login with role "creditManager"
    Then I verify grid "" column "Customer" row "[1]" contains "AGARWAL, ANGEL"
    Then I verify grid "" column "Funding Value" row "[1]" contains "$58,225.00"
    Then I verify grid "" column "Channel Specialist" row "[1]" contains "SMBone Specialist"
    When I click on record ""
#  Business tab Inputs and data verification
    Then I verify field "ABN" contains "68173780899"
    Then I verify field "Registered Business Name" contains "AGARWAL, ANGEL"
    Then I verify field "Business Address" contains "10 St Barnabas Bvd, Quinns Rocks WA 6030"
    Then I verify field "GST Registration Date" contains "27/04/2019"
    Then I verify field "Industry ANZSIC Code" contains "A AGRICULTURE, FORESTRY AND FISHING"
    Then I verify field "Industry Classification" contains "013 Fruit and Tree Nut Growing"
    Then I verify field "First Name" contains "darren"
    Then I verify field "Last Name" contains "davies"
    Then I verify field "Citizenship Status" contains "Australian Citizen"
    Then I verify field "Gender" contains "Male"
    Then I verify field "Residential Status" contains "Renting"
    Then I verify field "Residential Address" contains "34 Rochester Dr, Mindarie WA 6030"
    Then I verify field "Phone Number" contains "+61400000000"
    Then I verify field "Email" contains "chandu.nagu+60@gmail.com"
    Then I verify field "License Type" contains "Full Licence"
    Then I verify field "License Class" contains "Heavy Rigid (HR)"
    Then I verify field "License State" contains "NSW"
    Then I verify field "Drivers Licence Number" contains "88310877"
    Then I verify field "Income" contains "$30,000.00"
    Then I verify field "Expenses" contains "$2,000.00"
    Then I verify field "Income Frequency" contains "Monthly"
    Then I verify field "Expenses Frequency" contains "Monthly"
    Then I verify text "Overall Decision" is present
    Then I verify text "Owner" is present
    Then I verify text "Funding Value" is present
    Then I verify text "$58,225.00" is present
    Then I verify text "Monthly Repayment" is present
    Then I verify text "$1,378.66" is present
    Then I verify text "Customer Tier" is present
    Then I verify text "Application Criteria" is present
    Then I verify text "Low-Doc" is present
    Then I click on record view "Credit Rules"
#   Verifying content on 'Credit Rules'
    Then I verify text "Credit Rule Details" is present
    Then I verify grid "" column "Name" row "[1]" contains "ABN Age"
    Then I verify grid "" column "Rule Description" row "[1]" contains "ABN age is older than 1 year"
    Then I verify grid "" column "Value" row "[1]" contains "6 Years, 8 Months"
    Then I verify grid "" column "Result" row "[1]" contains "Approved"
    Then I verify grid "" column "Name" row "[2]" contains "Max Loan Amount"
    Then I verify grid "" column "Rule Description" row "[2]" contains "Total Loan Amount is less than $200,000"
    Then I verify grid "" column "Value" row "[2]" contains "$58,225.00"
    Then I verify grid "" column "Result" row "[2]" contains "Approved"
    Then I verify grid "" column "Name" row "[3]" contains "Min Applicant Age"
    Then I verify grid "" column "Rule Description" row "[3]" contains "Applicant age is greater than 25 years"
    Then I verify grid "" column "Value" row "[3]" contains "44 Years, 6 Months"
    Then I verify grid "" column "Result" row "[3]" contains "Approved"
    Then I verify grid "" column "Name" row "[4]" contains "Max Applicant Age"
    Then I verify grid "" column "Rule Description" row "[4]" contains "Applicant age is less than 70 years"
    Then I verify grid "" column "Value" row "[4]" contains "44 Years, 6 Months"
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
    Then I verify grid "" column "Name" row "[17]" contains "Luxury Vehicle (New)"
    Then I verify grid "" column "Rule Description" row "[17]" contains "Asset being financed is a New Passenger Car valued over $160K."
    Then I verify grid "" column "Value" row "[17]" contains "False"
    Then I verify grid "" column "Result" row "[17]" contains "Approved"
    Then I verify grid "" column "Name" row "[18]" contains "NZ Citizen"
    Then I verify grid "" column "Rule Description" row "[18]" contains "An Individual with NZ Citizenship has been identified"
    Then I verify grid "" column "Value" row "[18]" contains "False"
    Then I verify grid "" column "Result" row "[18]" contains "Approved"
    Then I verify grid "" column "Name" row "[19]" contains "Overall Decision"
    Then I verify grid "" column "Result" row "[19]" contains "Referred"
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
    Then I verify grid "" column "Uploaded By" row "[1]" contains "Andrew Wadham"
    Then I click on record view "Communications"
    Then I click on record view "Event History"
#    Verifying content on 'Event History'
    Then I verify text "Created Application" is present
    Then I verify text "Signed Privacy Statement" is present
    Then I verify text "Updated Application" is present
    Then I click on record view "Quote Details"
#    Verifying content on 'Quote Details'
    Then I verify text "Chattel Mortgage" is present
    Then I verify text "TOYOTA BZ4X" is present
    Then I verify field "Total Asset Cost" contains "$56,000.00"
    Then I verify field "Balloon Payment" contains "$16,800.00"
    Then I verify field "Total Cost of Loan" contains "$66,629.87"
    Then I verify field "Monthly Payment" contains "$1,378.66"
    Then I verify field "Specific Asset" contains "Yes"
    Then I verify field "New Asset" contains "Yes"
    Then I verify field "Payment Method" contains "Advance"
    Then I verify field "Pricing Type" contains "Dealer Pricing"
    Then I verify field "Asset Type" contains "Passenger or Light Commercial Vehicle"
    Then I verify field "Monthly GST" contains "$0.50"
    Then I verify field "Total GST" contains "$0.00"
    Then I verify field "Deposit Percentage" contains "2.68%"
    Then I verify field "Balloon Percentage" contains "30.00%"
    Then I verify field "Make" contains "TOYOTA"
    Then I verify field "Model" contains "BZ4X"
    Then I verify field "Status" contains "Proceeded"
    Then I verify field "Document" contains "Download"
    Then I verify field "Dealer Quote Document" contains "Download"
    Then I click on record view "Summary"
    Then I click on button "Take Ownership"
    Then I click on button "Done"
    Then I verify text "Testcredit Manager" is present
    Then I click on button "Update Credit Decision"
    Then I populate field "Decision" with "Declined"
    Then I populate field "Reason for Decline" with "Serviceability is not established"
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Declined" is present
    Then I verify text "Reason: Serviceability is not established" is present
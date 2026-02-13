@End2EndExcelOld
Feature: 02 SMB End2End Excel Old feature

  Background: Setup background and environment
    Given I setup browser
    Then I setup appian URL to "appian.active.url"
    And I setup appian version
    And I setup appian locale

  Scenario: TC001_Verify an existing ORIX Introducer is able to create a quote and submit an application
    Given I setup environment and login with role "introducer"
    Then I click on button from excel "excel:ButtonData.RequestAQuote"
    Then I populate field "Search Journeys" with excel "excel:ClientData.ABN"
    Then I click on button from excel "excel:ButtonData.SearchClient"
    Then I select grid "[2]" row "[1]"
    Then I click on button from excel "excel:ButtonData.Next"
    Then I click on element with text from excel "excel:RequestQuoteData.Asset Type"
    Then I click on element with text from excel "excel:RequestQuoteData.Specific Vehicle"
    Then I click on element with text from excel "excel:RequestQuoteData.New or Used"
    Then I click on element with text from excel "excel:RequestQuoteData.Pricing"
    Then I click on card direct from excel "excel:RequestQuoteData.Product Type"
    Then I populate field "Vehicle Category" with excel "excel:VehicleData.Vehicle Category"
    Then I populate field "Vehicle Description" with excel "excel:VehicleData.Vehicle Description"
    Then I populate field "Total Asset Cost (incl. GST)" with excel "excel:AmountBrokerage.Total Asset Cost (incl. GST)"
    Then I populating field "Deposit Amount" with excel "excel:AmountBrokerage.Deposit Amount"
    Then I populate field "Dealer Origination Fee (excl. GST)" with excel "excel:AmountBrokerage.Dealer Origination Fee (excl. GST)"
    Then I populate field "Brokerage % (excl. GST)" with excel "excel:AmountBrokerage.Brokerage % (excl. GST)"
    Then I populate field "Balloon %" with excel "excel:AmountBrokerage.Balloon %"
    Then I click on radio option from excel "excel:RequestQuoteData.Payment Method"
    Then I click on card direct from excel "excel:RequestQuoteData.Services"
    Then I click on term card from excel "excel:RequestQuoteData.Select a Term"
    Then I get field "Vehicle Category" value and store in excel "excel:OutputVehicleData.Vehicle Category"
    Then I get field "Vehicle Description" value and store in excel "excel:OutputVehicleData.Vehicle Description"
    Then I click on button from excel "excel:ButtonData.Next"
    Then I populate field "Make" with excel "excel:VehicleData.Make"
    Then I populate field "Model" with excel "excel:VehicleData.Model"
    Then I populate field "Sub Model" with excel "excel:VehicleData.Sub Model"
    Then I click on button from excel "excel:ButtonData.SearchVehicle"
    Then I click on button from excel "excel:ButtonData.SelectVehicle"
    Then I populate field "VIN" with excel "excel:VehicleData.VIN"
    Then I populate field "Registration Number" with excel "excel:VehicleData.Registration Number"
    Then I populate field "Engine Number" with excel "excel:VehicleData.Engine Number"
    Then I populate field "Other Comments" with excel "excel:VehicleData.Other Comments"
    Then I click on button from excel "excel:ButtonData.Next"
    Then I verify text "A value is required" is present
    Then I populate field "Quote Document" with excel "excel:UploadData.Quote Document"
    Then I click on button from excel "excel:ButtonData.Next"
    Then I verify text "Indicative Quote Summary" is present
    Then I verify text "Chattel Mortgage" is present
    Then I verify text "Your Passenger or Light Commercial Vehicle" is present
    Then I verify text "Passenger or Light Commercial Vehicle Description" is present
    Then I verify text "Total Cost of Loan" is present
    Then I verify text "Net Asset Cost (incl GST)" is present
    Then I click on button from excel "excel:ButtonData.CompleteQuote"
    Then I click on button from excel "excel:ButtonData.ProceedQuote"
    Then I click on element with text from excel "excel:RequestQuoteData.Application Type"
    Then I click on button from excel "excel:ButtonData.Next"
#   Business tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ClientData.ABN"
    Then I verify field "Registered Business Name" contains excel "excel:ClientData.Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:ClientData.Business Address"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:ClientData.Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:ClientData.Industry Classification"
    Then I populate field "Business Background" with excel "excel:ClientData.Business Background"
    Then I populate field "Asset Purpose" with excel "excel:ClientData.Asset Purpose"
    Then I click on button from excel "excel:ButtonData.Next"
    Then I verify field "First Name" contains excel "excel:ClientData.First Name"
    Then I verify field "Last Name" contains excel "excel:ClientData.Last Name"
    Then I verify field "Citizenship Status" contains excel "excel:ClientData.Citizenship Status"
    Then I verify field "Date Of Birth" contains excel "excel:ClientData.Date Of Birth"
    Then I verify field "Residential Status" contains excel "excel:ClientData.Residential Status"
    Then I verify field "Gender" contains excel "excel:ClientData.Gender"
    Then I verify field "Residential Address" contains excel "excel:ClientData.Residential Address"
    Then I verify field "Phone Number" contains excel "excel:ClientData.Phone Number"
    Then I verify field "Email" contains excel "excel:ClientData.Email"
    Then I verify field "License Type" contains excel "excel:ClientData.License Type"
    Then I verify field "License Class" contains excel "excel:ClientData.License Class"
    Then I verify field "License State" contains excel "excel:ClientData.License State"
    Then I verify field "Drivers Licence Number" contains excel "excel:ClientData.Drivers Licence Number"
    Then I click on button from excel "excel:ButtonData.Next"
#   Financial tab Inputs and data verification
    Then I verify field "Income" contains excel "excel:AmountBrokerage.Income"
    Then I verify field "Expenses" contains excel "excel:AmountBrokerage.Expenses"
    Then I click on button from excel "excel:ButtonData.Next"
#   Summary tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ClientData.ABN"
    Then I verify field "Registered Business Name" contains excel "excel:ClientData.Registered Business Name"
    Then I verify field "First Name" contains excel "excel:ClientData.First Name"
    Then I verify field "Last Name" contains excel "excel:ClientData.Last Name"
    Then I verify field "Income" contains excel "excel:AmountBrokerage.Income"
    Then I verify field "Expenses" contains excel "excel:AmountBrokerage.Expenses"
    Then I click on checkbox option "[1]"
    Then I click on checkbox option "[2]"
    Then I click on checkbox option "[3]"
    Then I click on checkbox option "[4]"
    Then I click on button from excel "excel:ButtonData.Submit"
    Then I click on element with text from excel "excel:RequestQuoteData.BEGIN"
    Then I click on element with text from excel "excel:RequestQuoteData.Send SMS"
    Then I verify text "The link sent via SMS will be active for 48 hours." is present
    Then I populates field "File Upload" with excel "excel:UploadData.File Upload"
    Then I click on button from excel "excel:ButtonData.Submit"
    Then I get grid "" column "Application Number" row "1" value and store in excel "excel:OutputApplicationGridData.Application Number"
    Then I get grid "" column "Quote Number" row "1" value and store in excel "excel:OutputApplicationGridData.Quote Number"
    Then I get grid "" column "Client (Applicant)" row "1" value and store in excel "excel:OutputApplicationGridData.Client (Applicant)"
    Then I get grid "" column "Application Status" row "1" value and store in excel "excel:OutputApplicationGridData.Application Status"
    Then I get grid "" column "Date Created" row "1" value and store in excel "excel:OutputApplicationGridData.Date Created"
    Then I get grid "" column "Last Updated User" row "1" value and store in excel "excel:OutputApplicationGridData.Last Updated User"

  Scenario: TC002_Verify if user is able to validate the credit rules and manually overwrite credit decision for Sole Trader
    Given I setup environment and login with role "creditManager"
    Then I verify grid "" column "Application" row "[1]" contains excel data "excel:OutputApplicationGridData.Application Number"
    Then I verify grid "" column "Customer" row "[1]" contains excel data "excel:ClientData.Registered Business Name"
    Then I verify grid "" column "Funding Value" row "[1]" contains excel data "excel:AmountBrokerage.Funding Value"
    Then I verify grid "" column "Channel Specialist" row "[1]" contains excel data "excel:ApplicationGridData.Channel Specialist"
    When I click on record from excel "excel:OutputApplicationGridData.Application Number"
#  Business tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ClientData.ABN"
    Then I verify field "Registered Business Name" contains excel "excel:ClientData.Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:ClientData.Business Address"
    Then I verify field "GST Registration Date" contains excel "excel:ClientData.GST Registration Date"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:ClientData.Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:ClientData.Industry Classification"
    Then I verify field "First Name" contains excel "excel:ClientData.First Name"
    Then I verify field "Last Name" contains excel "excel:ClientData.Last Name"
    Then I verify field "Citizenship Status" contains excel "excel:ClientData.Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:ClientData.Residential Status"
    Then I verify field "Gender" contains excel "excel:ClientData.Gender"
    Then I verify field "Residential Address" contains excel "excel:ClientData.Residential Address"
    Then I verify field "Phone Number" contains excel "excel:ClientData.Phone Number"
    Then I verify field "Email" contains excel "excel:ClientData.Email"
    Then I verify field "License Type" contains excel "excel:ClientData.License Type"
    Then I verify field "License Class" contains excel "excel:ClientData.License Class"
    Then I verify field "License State" contains excel "excel:ClientData.License State"
    Then I verify field "Drivers Licence Number" contains excel "excel:ClientData.Drivers Licence Number"
    Then I verify field "Income" contains excel "excel:AmountBrokerage.Income"
    Then I verify field "Expenses" contains excel "excel:AmountBrokerage.Expenses"
    Then I verify field "Income Frequency" contains excel "excel:AmountBrokerage.Income Frequency"
    Then I verify field "Expenses Frequency" contains excel "excel:AmountBrokerage.Expenses Frequency"
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
    Then I click on button from excel "excel:ButtonData.Equifax Check"
    Then I verify text "Equifax Check is in progress." is present
    Then I verify text "Click 'Done' to return to home screen." is present
    Then I click on button from excel "excel:ButtonData.Done"
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
    Then I verify field "Total Asset Cost" contains excel "excel:AmountBrokerage.Total Asset Cost (incl. GST)"
    Then I verify field "Balloon Payment" contains excel "excel:AmountBrokerage.Balloon Payment"
    Then I verify field "Total Cost of Loan" contains excel "excel:AmountBrokerage.Total Cost of Loan"
    Then I verify field "Monthly Payment" contains excel "excel:AmountBrokerage.Monthly Payment"
    Then I verify field "Specific Asset" contains excel "excel:AmountBrokerage.Specific Asset"
    Then I verify field "New Asset" contains excel "excel:AmountBrokerage.New Asset"
    Then I verify field "Payment Method" contains excel "excel:AmountBrokerage.Payment Method"
    Then I verify field "Pricing Type" contains excel "excel:AmountBrokerage.Pricing Type"
    Then I verify field "Asset Type" contains excel "excel:AmountBrokerage.Asset Type"
    Then I verify field "Monthly GST" contains excel "excel:AmountBrokerage.Monthly GST"
    Then I verify field "Total GST" contains excel "excel:AmountBrokerage.Total GST"
    Then I verify field "Deposit Percentage" contains excel "excel:AmountBrokerage.Deposit Percentage"
    Then I verify field "Balloon Percentage" contains excel "excel:AmountBrokerage.Balloon %"
    Then I verify field "Make" contains excel "excel:VehicleData.Make"
    Then I verify field "Model" contains excel "excel:VehicleData.Model"
    Then I verify field "Status" contains excel "excel:RequestQuoteData.Status"
    Then I verify field "Document" contains excel "excel:RequestQuoteData.Document"
    Then I verify field "Dealer Quote Document" contains excel "excel:RequestQuoteData.Dealer Quote Document"
    Then I click on record view "Summary"
    Then I click on button from excel "excel:ButtonData.Take Ownership"
    Then I click on button from excel "excel:ButtonData.Done"
    Then I verify text "Testcredit Manager" is present
    Then I click on button from excel "excel:ButtonData.Update Credit Decision"
    Then I populate field "Decision" with excel "excel:RequestQuoteData.Decision"
    Then I populate field "Reason for Decline" with excel "excel:RequestQuoteData.Reason for Decline"
    Then I click on button from excel "excel:ButtonData.Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Declined" is present
    Then I verify text "Reason: Serviceability is not established" is present


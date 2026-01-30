@End2End
Feature: 03 SMB End2End feature

  Background: Setup background and environment
    Given I setup browser
    Then I setup appian URL to "appian.active.url"
    And I setup appian version
    And I setup appian locale

  Scenario: TC001_Trust_Verify Introducer is able to Request a quote and Submit the application for New Asset and Dealer Pricing with Finance Lease with Services for Passenger or LCV
    Given I setup environment and login with role "introducer"
    Given I load test data for "TC001" from "03_SMB_End2End"
    Then I click on button "Request A Quote"
    Then I populate field "Search Journeys" with excel "excel:ABN"
    Then I click on button "SEARCH[2]"
    Then I select grid "[2]" row "[1]"
    Then I click on button "Next"
    Then I click on element with text from excel "excel:What type of asset are you seeking to finance?"
    Then I click on element with text from excel "excel:Do you have a specific passenger or light commercial vehicle in mind?"
    Then I click on element with text from excel "excel:Is the passenger or light commercial vehicle new?"
    Then I click on element with text from excel "excel:Do you want to use ORIX pricing?"
    Then I click on card direct from excel "excel:What product suits you best?"
    Then I populate field "Make" with excel "excel:Make"
    Then I populate field "Model" with excel "excel:Model"
    Then I populate field "Sub Model" with excel "excel:Sub Model"
    Then I click on button "SEARCH[2]"
    Then I click on button "SELECT VEHICLE"
    Then I populate field "Colour" with excel "excel:Colour"
    Then I populate field "Trim" with excel "excel:Trim"
    Then I populate field "Term" with excel "excel:Select a Term"
    Then I wait for "2" seconds
    Then I populate field "Annual Usage[2]" with excel "excel:Annual Usage"
    Then I populate field "Delivery State" with excel "excel:Delivery State"
    Then I populate field "Dealer Quote" with excel "excel:Quote Document"
    Then I populate field "Vehicle Category" with excel "excel:Vehicle or Asset Category"
    Then I populate field "Total Cost of Accessories (excl GST)" with excel "excel:Total Cost of Accessories (excl GST)"
    Then I populating field "Plate Cost (excl GST)" with excel "excel:Plate Cost (excl GST)"
    Then I populate field "Dealer Origination Fee" with excel "excel:Dealer Origination Fee (excl. GST)"
    Then I populate field "Discounts (excl GST)" with excel "excel:Discounts (excl GST)"
    Then I populate field "Stamp Duty (excl GST)" with excel "excel:Stamp Duty (excl GST)"
    Then I populate field "Luxury Car Tax (excl GST)" with excel "excel:Luxury Car Tax (excl GST)"
    Then I populate field "Registration Fee (excl GST)" with excel "excel:Registration Fee (excl GST)"
    Then I populate field "CTPI Fee (excl GST)" with excel "excel:CTPI Fee (excl GST)"
    Then I populate field "Delivery Fee (excl GST)" with excel "excel:Delivery Fee (excl GST)"
    Then I click on button "Next"
    Then I click on element with text from excel "excel:Services"
    Then I click on button "Generate Quote"
    Then I verify text "Indicative Quote Summary" is present
    Then I verify text "Finance Lease with Services" is present
    Then I verify text "Your Passenger or Light Commercial Vehicle" is present
    Then I verify text "Passenger or Light Commercial Vehicle Description" is present
    Then I verify text "Total Cost of Lease" is present
    Then I verify text "Total Asset Cost (incl GST)" is present
    Then I click on button "Close"
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
#    Financial tab Inputs and data verification
    Then I verify field "Justification" contains excel "excel:Justification"
    Then I click on button "Next"
#    Individual tab Inputs and data verification
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Date Of Birth" contains excel "excel:Date Of Birth"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Phone Number" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I click on button "Next"
    Then I verify field "ABN" contains "66004759611"
    Then I verify field "ACN" contains "004759611"
    Then I verify field "Company Name" contains "HONDA AUSTRALIA PTY. LTD."
    Then I click on button "Next"
    Then I verify field "Company Email" contains "monica@trust.co.au"
    Then I verify field "Phone Number" contains "+61448558866"
    Then I verify field "Business Background" contains "Agriculture"
    Then I click on button "Next"
#   Summary tab Inputs and data verification
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Registered Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I click on checkbox option "[1]"
    Then I click on checkbox option "[2]"
    Then I click on checkbox option "[3]"
    Then I click on checkbox option "[4]"
    Then I click on button "Submit"
    Then I wait for "2" seconds
    Then I populates field "File Upload[20]" with excel "excel:Trust Deed"
    Then I wait for "2" seconds
    Then I populates field "File Upload[26]" with excel "excel:Financial Statements"
    Then I click on button "Submit"
    Then I wait for "2" seconds
    Then I get grid "" column "Application Number" row "1" value and store in excel "excel:Application Number"
    Then I get grid "" column "Quote Number" row "1" value and store in excel "excel:Quote Number"
    Then I get grid "" column "Client (Applicant)" row "1" value and store in excel "excel:Client or Applicant"
    Then I get grid "" column "Application Status" row "1" value and store in excel "excel:Application Status"
    Then I get grid "" column "Date Created" row "1" value and store in excel "excel:Date Created"
    Then I get grid "" column "Last Updated User" row "1" value and store in excel "excel:Last Updated User"

  Scenario: TC002_Trust_Verify creditManager is able to validate the Quote details and Request for Information
    Given I setup environment and login with role "creditManager"
    Given I load test data for "TC002" from "03_SMB_End2End"
    When I click on record from excel "excel:Application Number"
#  Trust Details verification
    Then I verify field "Trust Name" contains excel "excel:Registered Business Name"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Registered Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Trust Type" contains excel "excel:Trust Type"
    Then I verify field "GST Registration Date" contains excel "excel:GST Registration Date"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify field "Will the vehicle/asset be stored at an address that is different to the address listed above?" contains excel "excel:Will the vehicle/asset be stored at an address that is different to the address listed above?"
    Then I verify field "Industry ANZSIC Code" contains excel "excel:Industry ANZSIC Code"
    Then I verify field "Industry Classification" contains excel "excel:Industry Classification"
    Then I verify field "Business Background" contains excel "excel:Business Background"
    Then I verify field "Asset Purpose" contains excel "excel:Asset Purpose"
#    Financial Information verification
    Then I verify field "Do you have sufficient ongoing monthly cashflow to meet your repayments under this facility taking into account your existing income and expenses, including all your debt & liabilities?" contains excel "excel:Do you have sufficient ongoing monthly cashflow to meet your repayments under this facility taking into account your existing income and expenses, including all your debt & liabilities?"
    Then I verify field "Justification" contains excel "excel:Justification"
#    Individual Trustees Information verification
    Then I verify field "First Name" contains excel "excel:First Name"
    Then I verify field "Last Name" contains excel "excel:Last Name"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
    Then I verify field "Date of Birth" contains excel "excel:Date Of Birth"
    Then I verify field "Phone Number" contains excel "excel:Phone Number"
    Then I verify field "Email" contains excel "excel:Email"
    Then I verify field "License Type" contains excel "excel:License Type"
    Then I verify field "License Class" contains excel "excel:License Class"
    Then I verify field "License State" contains excel "excel:License State"
    Then I verify field "Roles" contains excel "excel:Roles"
    Then I verify field "Percentage Rights" contains excel "excel:Percentage Rights"
#    Corporate Trustees Information verification
    Then I verify field "ABN[3]" contains excel "excel:Trustee 1 ABN"
    Then I verify field "ACN" contains excel "excel:ACN"
    Then I verify field "Company Name" contains excel "excel:Company Name"
    Then I verify field "Citizenship Status" contains excel "excel:Citizenship Status"
    Then I verify field "Residential Status" contains excel "excel:Residential Status"
#  Company Information 1 Details verification
    Then I verify text "66004759611 - HONDA AUSTRALIA PTY. LTD. Details" is present
    Then I verify field "Business Address[2]" contains "24 Dalman Pkwy, Glenfield Park NSW 2650"
    Then I verify field "Company Email" contains excel "excel:Email"
    Then I verify field "Phone Number[2]" contains excel "excel:Phone Number"
    Then I verify field "Business Background[2]" contains excel "excel:Business Background"
#  Right side pane Details verification
    Then I verify text "Overall Decision" is present
    Then I verify text "Owner" is present
    Then I verify text "Funding Value" is present
    Then I verify text "$56,342.61" is present
    Then I verify text "Monthly Repayment" is present
    Then I verify text "$1,497.35" is present
    Then I verify text "Customer Tier" is present
    Then I verify text "Application Criteria" is present
    Then I verify text "Full-Doc" is present
    Then I click on record view "Credit Rules"
#   Verifying content on 'Credit Rules'
    Then I verify text "This application has never been run validated against Credit Rules" is present
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
    Then I verify text "No items available" is present
    Then I click on record view "Document Extraction"
    Then I verify text "No items available" is present
    Then I click on record view "Documents"
#    Verifying content on 'Documents'
    Then I verify text "Uploaded Documents" is present
    Then I verify grid "" column "Document Name" row "[1]" contains "Trust_Deed.pdf"
    Then I verify grid "" column "Document Type" row "[1]" contains "Trust Deed"
    Then I verify grid "" column "Uploaded By" row "[1]" contains "Automation Introducer"
    Then I verify grid "" column "Document Name" row "[2]" contains "Financial_Statement_Document.pdf"
    Then I verify grid "" column "Document Type" row "[2]" contains "Financial Statements"
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
    Then I verify text "Finance Lease with Services" is present
    Then I verify text " AUDI A1" is present
    Then I verify field "Total Asset Cost" contains excel "excel:Total Asset Cost (incl. GST)"
    Then I verify field "GST" contains excel "excel:Total GST"
    Then I verify field "Total Cost of Lease" contains excel "excel:Total Cost of Lease"
    Then I verify field "Monthly Rental" contains excel "excel:Monthly Rental"
    Then I verify field "Specific Asset" contains excel "excel:Specific Asset"
    Then I verify field "New Asset" contains excel "excel:New Asset"
    Then I verify field "Pricing Type" contains excel "excel:Pricing Type"
    Then I verify field "Asset Type" contains excel "excel:Asset Type"
    Then I verify field "Monthly GST" contains excel "excel:Monthly GST"
    Then I verify field "Total GST" contains excel "excel:Total GST"
    Then I verify field "Residual Percentage" contains excel "excel:Residual Percentage"
    Then I verify field "Asset Category[2]" contains excel "excel:Vehicle or Asset Category"
    Then I verify field "Business" contains excel "excel:Registered Business Name"
    Then I verify field "Introducer" contains "Dandenong Mits Pty Ltd"
    Then I verify field "Annual Usage" contains excel "excel:Annual Usage"
    Then I verify field "Discounts (excl GST)" contains excel "excel:Discounts (excl GST)"
    Then I verify field "Registration fee (excl GST)" contains excel "excel:Registration Fee (excl GST)"
    Then I verify field "Delivery State" contains excel "excel:Delivery State"
    Then I verify field "Status" contains excel "excel:Status"
    Then I verify field "Dealer Quote Document" contains excel "excel:Dealer Quote Document"
    Then I click on record view "Summary"
    Then I click on button "Take Ownership"
    Then I wait for "2" seconds
    Then I click on button "Done"
    Then I verify text "Automation Credit Manager" is present
    Then I click on button "Request Information"
    Then I verify text "Request for Further Information" is present
    Then I verify text "Request Body" is present
    Then I verify text "Hi Automation Introducer" is present
    Then I click on element with text "GST registered less than 3 months."
    Then I click on element with text "Current Tax Portals."
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Awaiting Information" is present

  Scenario: TC003_Trust_Verify Introducer is able to view and Respond to the Notifications
    Given I setup environment and login with role "introducer"
    Given I load test data for "TC003" from "03_SMB_End2End"
    Then I verify text "Request for further information" is present
    Then I verify text "Additional information is required for this Credit Application. Navigate to your messages to respond" is present
    Then I click on element with text "Communications"
    Then I verify text "Select a message to view the details" is present
    Then I click on element with text "ENQ-556"
    Then I verify text "APP-556: Request for Further Information" is present
    Then I verify text "Some further information is required to process your application." is present
    Then I verify text "Please click the action below to provide the requested documents to finalize your application." is present
    Then I click on element with text "Respond to Request"
    Then I verify text "APP-556: Request for Further Information" is present
    Then I verify text "Hi Automation Introducer," is present
    Then I verify text "Before we can finalise your application, we will need additional information/documents." is present
    Then I verify text "The information/documents needed are: GST registered less than 3 months. Current Tax Portals." is present
    Then I populate field "Comments" with "Responding back for the requested information"
    Then I click on button "Submit"
    Then I click on element with text "Dashboard"
    Then I verify grid "" column "Application Number" row "[1]" contains excel data "excel:Application Number"
    Then I verify grid "" column "Quote Number" row "[1]" contains excel data "excel:Quote Number"
    Then I verify grid "" column "Client (Applicant)" row "[1]" contains excel data "excel:Registered Business Name"
    Then I verify grid "" column "Application Status" row "[1]" contains "Information Received"
    Then I click on element with text "Communications"
    Then I verify text "Select a message to view the details" is present
    Then I click on element with text "ENQ-556"
    Then I verify text "APP-556: Request for Further Information" is present
    Then I verify text "Response Received" is present
    Then I verify text "Thank you for responding to this request for further information." is present
    Then I verify text "You will be notified of the next steps as the information you have provided has been assessed." is present

  Scenario: TC004_Trust_Verify creditManager is able to overwrite the credit decision
    Given I setup environment and login with role "creditManager"
    Given I load test data for "TC004" from "03_SMB_End2End"
    Then I verify grid "" column "Application" row "[1]" contains excel data "excel:Application Number"
    Then I verify grid "" column "Customer" row "[1]" contains excel data "excel:Registered Business Name"
    Then I verify grid "" column "Assignee" row "[1]" contains excel data "excel:Credit Officer"
    Then I verify grid "" column "Application Status" row "[1]" contains "Information Received"
    When I click on record from excel "excel:Application Number"
    Then I verify field "ABN" contains excel "excel:ABN"
    Then I verify field "Registered Business Name" contains excel "excel:Registered Business Name"
    Then I verify field "Business Address" contains excel "excel:Business Address"
    Then I verify text "Information Received" is present
    Then I click on button "Update Credit Decision"
    Then I populate field "Decision" with excel "excel:Decision"
    Then I populate field "Conditions" with excel "excel:Conditions of Approval"
    Then I click on button "Submit"
    Then I verify text "Overall Decision" is present
    Then I verify text "Conditional Approval" is present
    Then I verify text "Copy of current council rates notice to be provided to confirm property ownership." is present
    Then I verify text "Contract to be executed in full by all required parties." is present
    Then I verify text "Certificate of Currency noting ORIX Australia Corporation Limited as an interested party to be provided." is present
    Then I verify text "Tax Invoice to be provided before settlement." is present
    Then I verify text "Supplier, Asset and Pricing to be acceptable to ORIX Australia Corporation Limited" is present
    Then I populate field with placeholder "Add a new comment" with "Approved this quote with above conditions"
    Then I click on checkbox option "[1]"
    Then I click on button "Save"
    Then I verify text "Approved this quote with above conditions" is present
    Then I click on element with text "Dashboard"
    Then I verify grid "" column "Application" row "[1]" contains excel data "excel:Application Number"
    Then I verify grid "" column "Customer" row "[1]" contains excel data "excel:Registered Business Name"
    Then I verify grid "" column "Assignee" row "[1]" contains excel data "excel:Credit Officer"
    Then I verify grid "" column "Application Status" row "[1]" contains "Conditional Approval"
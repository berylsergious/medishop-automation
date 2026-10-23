Feature: Order History Management
  As a registered MediShop customer
  I want to view and manage my order history
  So that I can track my past purchases and monitor current orders

  Background:
    Given User logs in with valid credentials username "${medishop_username}" and password "${medishop_password}"
    And the user navigates to the "Orders" page

  # ==========================================
  # UI & DEFAULT STATE
  # ==========================================

  @smoke @ui
  Scenario: Verify default state and order summary display
    Then the orders page should be displayed
    And the page header should be "Your orders"
    And the page subtitle should be "Every order placed from this browser, newest first."
    And the "Filter by status" dropdown should display "All statuses"
    And the "Search orders" input field should be empty

@regression @search
  Scenario Outline: Search orders by valid Product Name
    When the user enters "<SearchTerm>" into the "Search orders" field
    Then the order count summary should display "<ExpectedCount>"
    And the order card for "<ExpectedOrderID>" should be displayed in right format

    Examples:
      | SearchTerm        | ExpectedCount | ExpectedOrderID |
      | Amoxicillin 500mg | 1 order found | #MS-9102-BY     |
      | Vitamin           | 1 order found | #MS-9255-CZ     |
      | Atorvastatin      | 2 order found | #MS-8492-AX     |

  @regression @search @negative
  Scenario: Search with non-existent data displays empty state
    When the user enters "Paracetamol" into the "Search orders" field
    Then a "This message should not exist" and "This description should not exist" message should be displayed
    And the user should see the "Reset" button
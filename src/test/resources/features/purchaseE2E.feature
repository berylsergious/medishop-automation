Feature: Product Purchase feature

Background: 
Given User logs in with valid credentials username "<username>" and password "<password>"
|username               |password               |
|${medishop_username}   |${medishop_password}   |
@e2e
Scenario: Upload prescription after filling delivery and payment details
Given user is in the homepage
# And user search for a "<product>"
# And user adds the "<product>" to the cart
# When cart is updated with the "<product>"
# And user clicks checkout
# Then user should be in Delivery & Payment page

# And user fills the delivery address:
#     | field        | value               |
#     | name         | John Doe            |
#     | phone        | 9876543210          |
#     | address      | 123 Main Street     |
#     | city         | Bangalore           |
#     | state        | Karnataka           |
#     | pincode      | 560001              |

# And user fills the payment details:
#     | field       | value               |
#     | cardNumber  | 4111111111111111    |
#     | expiry      | 12/30               |
#     | cvv         | 123                 |
#     | cardName    | John Doe            |
# And user uploads prescription
# And navigate back to Delivery & payment and fill the details
# And user place the order
# Then placed order should be present in orders page


# Scenario Outline:  Upload prescription before filling delivery and payment details
# Given user is in the homepage
# And user search for the "<product>"
# And user adds the "<product>" to the cart
# When cart is updated with the "<product>"
# And user clicks checkout
# Then user should be in Delivery & Payment page

# When user uploads prescription 
# And user navigate back to Delivery & payment

# And user fills the delivery address:
#     | field        | value               |
#     | name         | John Doe            |
#     | phone        | 9876543210          |
#     | address      | 123 Main Street     |
#     | city         | Bangalore           |
#     | state        | Karnataka           |
#     | pincode      | 560001              |

# And user fills the payment details:
#     | field       | value               |
#     | cardNumber  | 4111111111111111    |
#     | expiry      | 12/30               |
#     | cvv         | 123                 |
#     | cardName    | John Doe            |

# And user place the order
# Then placed order should be present in orders page

# Examples:
#     | product       |
#     | Paracetamol   |
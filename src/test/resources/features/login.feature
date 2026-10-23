Feature: Login functionality

Background: 
    Given the user is in the MediShop login page


@smoke
Scenario Outline: Login Validation
When user enters credentials username "<username>" and password "<password>"
And clicks the Sign In button
Then the login result should be "<result>"

Examples:
|username               |password               |result             |
|${medishop_username}   |${medishop_password}   |success            |
|${medishop_username}   |invalidPassword        |error              |
|invalidUsername        |${medishop_password}   |invalidemailalert  |
|jake1rufus@gmail.com   |invalidPassword        |error              |
|                       |invalidPassword        |usernamerequired   |
|jake1rufus@gmail.com   |                       |passwordrequired   |
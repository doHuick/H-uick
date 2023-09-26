// SPDX-License-Identifier: MIT
pragma solidity ^0.8.21;

enum Status { InProgress, Complete }

/* 계약 정보 */
struct Contract {
    string hashOfDocs;
    Status status;
    address lenderWallet;
    address borrowerWallet;
    uint256 principalAmount;
    uint256 interestRate;
    uint256 issueDate;
    uint256 maturityDate;
}
// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

contract ContractData {
    enum Status { InProgress, Complete }    

    /* 계약 정보 */
    struct Contract {
        bytes32 hashOfDocs;
        Status status;
        address lenderWallet;
        address borrowerWallet;
        uint256 principalAmount;
        uint256 interestRate;
        uint256 issueDate;
        uint256 maturityDate;
    }
}
// SPDX-License-Identifier: MIT
pragma solidity ^0.8.21;

import "./ContractDTO.sol";

contract LoanContract {
    event ConstructorCalled(address indexed sender, address huickService);
    address public huickService;

    constructor() {
        huickService = msg.sender;
        emit ConstructorCalled(msg.sender, huickService);
    }

    modifier onlyService() {
        require(msg.sender == huickService, "Only H-uick can call this function!");
        _;
    }

    event ContractCreated(string hashOfDocs);
    event ContractUpdated(string hashOfDocs, Status status);

    mapping (string => Contract) public contracts; // 해시값을 키로 계약 구조체를 매핑

    // 새로운 계약 생성
    function createContract(string memory _hashOfDocs, address _lenderWallet, address _borrowerWallet, uint _principalAmount, uint _interestRate, uint _issueDate, uint256 _maturityDate) public onlyService {
        Contract memory newContract = Contract(_hashOfDocs, Status.InProgress, _lenderWallet, _borrowerWallet, _principalAmount, _interestRate, _issueDate, _maturityDate);
        contracts[_hashOfDocs] = newContract;
        emit ContractCreated(_hashOfDocs);
    }

    // 계약 상태 변경
    function updateContractStatus(string memory _hashOfDocs, Status _status) public onlyService {
        require(bytes(contracts[_hashOfDocs].hashOfDocs).length != 0, "Contract does not exist");
        contracts[_hashOfDocs].status = _status;
        emit ContractUpdated(_hashOfDocs, _status);
    }
}
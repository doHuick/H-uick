// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "./ContractData.sol";

contract LoanContract is ContractData {
    event ConstructorCalled(address indexed sender, address service);
    address public service;

    constructor() public {
        service = msg.sender;
        emit ConstructorCalled(msg.sender, service);
    }

    modifier onlyService() {
        require(msg.sender == service, "Only H-uick can call this function!");
        _;
    }

    event ContractCreated(bytes32 hashOfDocs);
    event ContractUpdated(bytes32 hashOfDocs, Status status);

    mapping (address => bytes32[]) public lenderContracts;
    mapping (address => bytes32[]) public borrowerContracts;
    mapping (bytes32 => Contract) public contracts; // 해시값을 키로 계약 구조체를 매핑

    // 해시 계산
    function computeHash(string memory _data) internal pure returns (bytes32) {
        return keccak256(abi.encodePacked(_data));
    }

    // 새로운 계약 생성
    function createContract(string memory _hashOfDocs, address _lenderWallet, address _borrowerWallet, uint _principalAmount, uint _interestRate, uint _issueDate, uint _maturityDate) public onlyService {
        bytes32 hash = computeHash(_hashOfDocs);
        Contract memory newContract = Contract(hash, Status.InProgress, _lenderWallet, _borrowerWallet, _principalAmount, _interestRate, _issueDate, _maturityDate);
        contracts[hash] = newContract;
        lenderContracts[_lenderWallet].push(hash);
        borrowerContracts[_borrowerWallet].push(hash);
        emit ContractCreated(hash);
    }

    // 계약 상태 변경
    function updateContractStatus(string memory _hashOfDocs, Status _status) public onlyService {
        bytes32 hash = computeHash(_hashOfDocs);
        require(contracts[hash].hashOfDocs == hash, "Contract does not exist");
        require(contracts[hash].status != Status.Complete, "Contract already completed");
        contracts[hash].status = _status;
        emit ContractUpdated(hash, _status);
    }

    //계약 조회
    function getLenderContracts(address _lender) public view returns (bytes32[] memory) {
        return lenderContracts[_lender];
    }

    function getBorrowerContracts(address _borrower) public view returns (bytes32[] memory) {
        return borrowerContracts[_borrower];
    }
}
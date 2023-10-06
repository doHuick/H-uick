const LoanContract = artifacts.require("LoanContract");
const ContractData = artifacts.require("ContractData");

contract("LoanContract", accounts => {
  let loanContract;

  before(async () => {
    const contractData = await ContractData.new();
    loanContract = await LoanContract.new();
  });

  it("should set service address to the address of the creator", async () => {
    const service = await loanContract.service();
    assert.equal(service, accounts[0], "Service address is not set correctly");
  });

  it("should create a new contract", async () => {
    const hashOfDocs = "document_hash";
    const lenderWallet = accounts[1];
    const borrowerWallet = accounts[2];
    const principalAmount = 1000;
    const interestRate = 5;
    const issueDate = Math.floor(Date.now() / 1000);
    const maturityDate = issueDate + 10000;

    await loanContract.createContract(
      hashOfDocs, lenderWallet, borrowerWallet, principalAmount, interestRate, issueDate, maturityDate, { from: accounts[0] }
    );

    const contractHash = web3.utils.keccak256(hashOfDocs);
    const contractData = await loanContract.contracts(contractHash);

    assert.equal(contractData.hashOfDocs, contractHash, "Hash of documents is incorrect");
    assert.equal(contractData.lenderWallet, lenderWallet, "Lender wallet is incorrect");
    assert.equal(contractData.borrowerWallet, borrowerWallet, "Borrower wallet is incorrect");
    assert.equal(contractData.principalAmount, principalAmount, "Principal amount is incorrect");
    assert.equal(contractData.interestRate, interestRate, "Interest rate is incorrect");
    assert.equal(contractData.issueDate, issueDate, "Issue date is incorrect");
    assert.equal(contractData.maturityDate, maturityDate, "Maturity date is incorrect");
  });

  it("should update contract status", async () => {
    const hashOfDocs = "document_hash_to_update";
    const lenderWallet = accounts[3];
    const borrowerWallet = accounts[4];
    const principalAmount = 2000;
    const interestRate = 6;
    const issueDate = Math.floor(Date.now() / 1000);
    const maturityDate = issueDate + 20000;
  
    // 먼저 새로운 계약을 생성
    await loanContract.createContract(
      hashOfDocs, lenderWallet, borrowerWallet, principalAmount, interestRate, issueDate, maturityDate, { from: accounts[0] }
    );
  
    // 상태 업데이트
    await loanContract.updateContractStatus(hashOfDocs, 1, { from: accounts[0] }); // 1은 Status.Complete에 대응
  
    const contractHash = web3.utils.keccak256(hashOfDocs);
    const updatedContract = await loanContract.contracts(contractHash);
  
    assert.equal(updatedContract.status.toNumber(), 1, "Status is not updated"); // 업데이트된 상태 확인
  });

  it("should retrieve contracts for a lender", async () => {
    const lenderWallet = accounts[1];
    const contractHashes = await loanContract.getLenderContracts(lenderWallet);
    
    assert.equal(contractHashes.length, 1, "There should be one contract for the lender");
    assert.equal(contractHashes[0], web3.utils.keccak256("document_hash"), "Contract hash is incorrect for the lender");
  });
  
  it("should retrieve contracts for a borrower", async () => {
    const borrowerWallet = accounts[2];
    const contractHashes = await loanContract.getBorrowerContracts(borrowerWallet);
    
    assert.equal(contractHashes.length, 1, "There should be one contract for the borrower");
    assert.equal(contractHashes[0], web3.utils.keccak256("document_hash"), "Contract hash is incorrect for the borrower");
  });
  
});

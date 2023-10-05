import web3 from './web3';
import LoanContractABI from './LoancontractABI';

const contractAddress = "0x63E3AD30D7B9c0cdBc5c85c53195420fa17e5Cd1";
const SERVER_ADDRESS = import.meta.env.VITE_SERVER_ADDRESS;
const SERVER_KEY = import.meta.env.VITE_SERVER_KEY;

export const createContract = async (
  hashOfDocs: string,
  lenderWallet: string,
  borrowerWallet: string,
  principalAmount: number,
  interestRate: number,
  issueDate: number,
  maturityDate: number
) => {
  const contract: any = new web3.eth.Contract(LoanContractABI, contractAddress);

  web3.eth.defaultAccount = SERVER_ADDRESS;

  const currentNonce = await web3.eth.getTransactionCount(SERVER_ADDRESS, 'pending');

  const estimatedGas = await contract.methods.createContract(
    hashOfDocs, lenderWallet, borrowerWallet, principalAmount, interestRate, issueDate, maturityDate
  ).estimateGas({ from: SERVER_ADDRESS });

  const estimatedGasBigInt = BigInt(estimatedGas);
const gasLimit = Number(estimatedGasBigInt * BigInt(120) / BigInt(100));

  const data = await contract.methods.createContract(
    hashOfDocs, lenderWallet, borrowerWallet, principalAmount, interestRate, issueDate, maturityDate
  ).encodeABI({ from: SERVER_ADDRESS });

  const transactionParams = {
    nonce: currentNonce,
    gasPrice: await web3.eth.getGasPrice(),
    gasLimit: gasLimit,
    to: contractAddress,
    data: data,
    value: 0
  };

  const signedTransaction = await web3.eth.accounts.signTransaction(transactionParams, SERVER_KEY);
  // console.log(signedTransaction);

  try {
    const receipt = await web3.eth.sendSignedTransaction(signedTransaction.rawTransaction as string);
    // console.log("기록 완료!!! : ", receipt.transactionHash);
    return receipt.transactionHash;
  } catch (error) {
    console.error("트랜잭션 생성 중 오류 발생:", error);
    throw new Error(`Error while creating contract: ${error}`);
  }
}

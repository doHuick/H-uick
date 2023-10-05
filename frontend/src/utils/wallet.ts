import web3 from './web3';

export const createWallet = (): [string, string] => {
  const { address, privateKey } = web3.eth.accounts.create();

  return [address, privateKey];
}
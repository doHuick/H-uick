module.exports = function(callback) {
  // 랜덤한 지갑 생성
  const wallet = web3.eth.accounts.create();

  // 지갑 정보 출력
  console.log('Address:', wallet.address);
  console.log('Private Key:', wallet.privateKey);

  callback();
};

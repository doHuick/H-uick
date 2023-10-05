import Web3 from 'web3';

const INFURA_API = import.meta.env.VITE_INFURA_API;
const web3 = new Web3(new Web3.providers.HttpProvider(`https://sepolia.infura.io/v3/${INFURA_API}`));

export default web3;
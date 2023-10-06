const LoanContractABI: any[] = [
  {
    "constant": true,
    "inputs": [
      {
        "name": "",
        "type": "address"
      },
      {
        "name": "",
        "type": "uint256"
      }
    ],
    "name": "borrowerContracts",
    "outputs": [
      {
        "name": "",
        "type": "bytes32"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [
      {
        "name": "",
        "type": "address"
      },
      {
        "name": "",
        "type": "uint256"
      }
    ],
    "name": "lenderContracts",
    "outputs": [
      {
        "name": "",
        "type": "bytes32"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [],
    "name": "service",
    "outputs": [
      {
        "name": "",
        "type": "address"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [
      {
        "name": "",
        "type": "bytes32"
      }
    ],
    "name": "contracts",
    "outputs": [
      {
        "name": "hashOfDocs",
        "type": "bytes32"
      },
      {
        "name": "status",
        "type": "uint8"
      },
      {
        "name": "lenderWallet",
        "type": "address"
      },
      {
        "name": "borrowerWallet",
        "type": "address"
      },
      {
        "name": "principalAmount",
        "type": "uint256"
      },
      {
        "name": "interestRate",
        "type": "uint256"
      },
      {
        "name": "issueDate",
        "type": "uint256"
      },
      {
        "name": "maturityDate",
        "type": "uint256"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  },
  {
    "inputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "constructor"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": true,
        "name": "sender",
        "type": "address"
      },
      {
        "indexed": false,
        "name": "service",
        "type": "address"
      }
    ],
    "name": "ConstructorCalled",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "name": "hashOfDocs",
        "type": "bytes32"
      }
    ],
    "name": "ContractCreated",
    "type": "event"
  },
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "name": "hashOfDocs",
        "type": "bytes32"
      },
      {
        "indexed": false,
        "name": "status",
        "type": "uint8"
      }
    ],
    "name": "ContractUpdated",
    "type": "event"
  },
  {
    "constant": false,
    "inputs": [
      {
        "name": "_hashOfDocs",
        "type": "string"
      },
      {
        "name": "_lenderWallet",
        "type": "address"
      },
      {
        "name": "_borrowerWallet",
        "type": "address"
      },
      {
        "name": "_principalAmount",
        "type": "uint256"
      },
      {
        "name": "_interestRate",
        "type": "uint256"
      },
      {
        "name": "_issueDate",
        "type": "uint256"
      },
      {
        "name": "_maturityDate",
        "type": "uint256"
      }
    ],
    "name": "createContract",
    "outputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "constant": false,
    "inputs": [
      {
        "name": "_hashOfDocs",
        "type": "string"
      },
      {
        "name": "_status",
        "type": "uint8"
      }
    ],
    "name": "updateContractStatus",
    "outputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [
      {
        "name": "_lender",
        "type": "address"
      }
    ],
    "name": "getLenderContracts",
    "outputs": [
      {
        "name": "",
        "type": "bytes32[]"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [
      {
        "name": "_borrower",
        "type": "address"
      }
    ],
    "name": "getBorrowerContracts",
    "outputs": [
      {
        "name": "",
        "type": "bytes32[]"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  }

];

export default LoanContractABI;
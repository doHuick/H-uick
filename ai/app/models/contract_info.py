from pydantic import BaseModel
from typing import Optional

class LoanDetails(BaseModel):
    loanAmount: int
    loanAmountInKorean: str
    borrowedDate: str

class RepaymentDetails(BaseModel):
    repaymentMethod: str
    repaymentCondition: Optional[str]
    maturityDate: Optional[str]

class PersonInfo(BaseModel):
    name: str
    address: Optional[str]
    ssn: Optional[str]  # 주민등록번호
    contact: Optional[str]

class ContractInfo(BaseModel):
    loanDetails: LoanDetails
    repaymentDetails: RepaymentDetails
    debtorInfo: PersonInfo
    creditorInfo: PersonInfo
    specialAgreement: Optional[str]

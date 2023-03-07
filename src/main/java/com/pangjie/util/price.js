import Decimal from 'decimal.js';


export function numberDiv(arg1, arg2) {
  return new Decimal(arg1).div(new Decimal(arg2)).toNumber();
}
export function numberMul(arg1, arg2) {
  return new Decimal(arg1).mul(new Decimal(arg2)).toNumber();
}
export function numberAdd(arg1, arg2) {
  return new Decimal(arg1).add(new Decimal(arg2)).toNumber();
}
export function numberSub(arg1, arg2) {
  return new Decimal(arg1).sub(new Decimal(arg2)).toNumber();
}

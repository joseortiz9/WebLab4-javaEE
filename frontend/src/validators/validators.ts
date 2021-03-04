export const isNumber = (obj: any) => !isNaN(parseFloat(obj));
export const min = (min: number, actual: number) => actual >= min;
export const max = (max: number, actual: number) => actual <= max;
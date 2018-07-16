const subscribers = [];
const errors = [];

export const subscribe = callback => subscribers.push(callback);
export const logError = error => subscribers.forEach(s => s(error));
export const getErrors = () => errors.slice();

subscribe(e => errors.push(e));
subscribe(e => console.error(e));

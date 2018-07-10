const subscribers = [];

export const subscribe = (callback) => subscribers.push(callback);
export const logError = (error) => subscribers.forEach(s => s(error));

subscribe(e => console.error(e));
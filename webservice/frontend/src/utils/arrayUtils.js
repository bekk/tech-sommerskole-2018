export const compareByKey = ({ getKey, reverse = false }) => (a, b) => {
  let result = 0;
  const keyA = getKey(a);
  const keyB = getKey(b);
  const aIsUndefined = typeof (keyA) === 'undefined';
  const bIsUndefined = typeof (keyB) === 'undefined';
  if (aIsUndefined || bIsUndefined) {
    if (aIsUndefined) {
      result = bIsUndefined ? 0 : -1;
    } else {
      result = 1;
    }
  } else {
    if (keyA > keyB) result = 1;
    if (keyA < keyB) result = -1;
  }
  if (reverse) result *= -1;
  return result;
};

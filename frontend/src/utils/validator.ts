export const idValidator = (id: string): boolean => {
  if (id.length < 4 || id.length > 16) {
    return false;
  }
  return true;
};

export const passwordValidator = (password: string): boolean => {
  if (password.length < 4 || password.length > 16) {
    return false;
  }
  return true;
};

export const descValidator = (desc: string): boolean => {
  if (desc.length < 0) {
    return false;
  }
  return true;
};

export const nicknameValidator = (nickname: string): boolean => {
  if (nickname.length < 0) {
    return false;
  }
  return true;
};

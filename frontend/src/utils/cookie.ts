import { Cookies } from "react-cookie";
import { Cookie, CookieSetOptions } from "universal-cookie";

const cookies = new Cookies();

export const setCookie = (
  name: string,
  value: string,
  options?: CookieSetOptions
) => {
  return cookies.set(name, value, { ...options });
};

export const getCookie = (name: any) => {
  return cookies.get(name);
};

export const removeCookie = (name: string, options?: CookieSetOptions) => {
  try {
    cookies.remove(name, { ...options });
  } catch (error) {
    console.log(error);
  }
};

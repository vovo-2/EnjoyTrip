import { endpoint } from "@/config";

export async function postLogin({ userId, userPassword }) {
  const data = {
    ...(userId && { userId }),
    ...(userPassword && { userPassword }),
  };
  return this.getMethod("post", {
    url: `${endpoint.user}/login`,
    data,
  });
}
export async function postUser({
  userName,
  userPassword,
  userId,
  emailId,
  emailDomain,
}) {
  const data = {
    ...(userName && { userName }),
    ...(userPassword && { userPassword }),
    ...(userId && { userId }),
    ...(emailId && { emailId }),
    ...(emailDomain && { emailDomain }),
  };
  return this.getMethod("post", {
    url: `${endpoint.user}/sign`,
    data,
  });
}

export async function findById({ userId }) {
  const params = {
    ...(userId && { userId }),
  };
  this.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  return this.getMethod("get", {
    url: `${endpoint.user}/info/${userId}`,
    params,
  });
}

export async function tokenRegeneration({ user }) {
  this.defaults.headers["refreshToken"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  const data = {
    ...(user && user),
  };
  return this.getMethod("post", {
    url: `${endpoint.user}/refresh`,
    data,
  });
}

export async function logout({ userId }) {
  const params = {
    ...(userId && { userId }),
  };
  return this.getMethod("get", {
    url: `${endpoint.user}/logout/${userId}`,
    params,
  });
}

import { endpoint } from "@/config";

export async function getSido() {
  return this.getMethod("get", {
    url: `${endpoint.attraction}/sido`,
  });
}

export async function getContentType() {
  return this.getMethod("get", {
    url: `${endpoint.attraction}/content`,
  });
}

export async function getGuGun({ sido }) {
  const params = {
    ...(sido && { sido }),
  };
  return this.getMethod("get", {
    url: `${endpoint.attraction}/gugun`,
    params,
  });
}

export async function getSearch({
  sidoCode,
  gugunCode,
  contentTypeId,
  key,
  word,
}) {
  const params = {
    sidoCode,
    gugunCode,
    contentTypeId,
    ...(key && { key }),
    ...(word && { word }),
  };
  return this.getMethod("get", {
    url: `${endpoint.attraction}/search`,
    params,
  });
}

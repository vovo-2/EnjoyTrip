import { endpoint } from "@/config";

export async function getArticles({ key, word, lastArticleNo, listSize }) {
  console.log(lastArticleNo);
  const params = {
    ...(key && { key }),
    ...(word && { word }),
    ...(lastArticleNo && { lastArticleNo }),
    ...(listSize && { listSize }),
  };
  return this.getMethod("get", {
    url: `${endpoint.board}`,
    params,
  });
}

export async function putArticle() {
  return this.getMethod("put", {
    url: `${endpoint.board}`,
    data,
  });
}

export async function postArticle({ subject, content, imageFiles, userId }) {
  this.defaults.headers["Content-Type"] = null;
  let data = new FormData();
  data.append("subject", subject);
  data.append("content", content);
  data.append("userId", userId);
  if (imageFiles.length > 0) {
    data.append("imageFiles", imageFiles);
  }
  for (const pair of data) {
    console.log(pair);
  }
  return this.getMethod("post", {
    url: `${endpoint.board}`,
    data,
  });
}

export async function getArticle({ articleNo }) {
  return this.getMethod("get", {
    url: `${endpoint.board}/${articleNo}`,
  });
}

export async function deleteArticle({ articleNo }) {
  return this.getMethod("delete", {
    url: `${endpoint.board}/${articleNo}`,
    params,
  });
}

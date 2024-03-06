import axios from "axios";
import apis from "./modules";
class ApiClient extends axios.Axios {
  /**
   @constructor
   @param {object} config
   @param {object} options
   */
  constructor(options, categories) {
    const axiosConfig = ApiClient.transformToAxiosConfig(options);
    super(axiosConfig);

    this.axiosConfig = axiosConfig;
    if (categories) {
      this.categories = Object.keys(apis)
        .filter((key) => {
          const findItem = categories.findIndex((category) => category === key);
          return findItem > -1 && apis[key];
        })
        .map((key) => key);
    } else {
      this.categories = Object.keys(apis).map((key) => key);
    }
    this.attachAPIs(this.categories);
  }
  attachAPIs(categories) {
    categories.forEach((category) => {
      this[category] = {};
      Object.keys(apis[category]).forEach((methodName) => {
        this[category][methodName] = apis[category][methodName].bind(this);
      });
    });
  }

  static transformToAxiosConfig(options) {
    let axiosOptions = options;
    if (!axiosOptions || typeof axiosOptions !== "object") {
      axiosOptions = {};
      if (!axiosOptions.baseURL) {
        throw new Error("please pass baseUrl");
      }
    }

    const baseURL = axiosOptions.baseURL;
    const axiosConfig = Object.assign(
      {
        baseURL,
        headers: {
          "Content-Type": "application/json",
        },
      },
      axiosOptions
    );

    return axiosConfig;
  }
  getMethod(method, { url, params = {}, data = {}, headers }) {
    if (method === "get") {
      return this.get(
        url,
        {
          params,
        },
        { headers: headers }
      );
    } else if (method === "post") {
      return this.post(url, JSON.stringify(data), {
        headers: headers,
      });
    } else if (method === "put") {
      return this.put(url, JSON.stringify(data), {
        headers: headers,
      });
    } else if (method === "delete") {
      return this.delete(
        url,
        { ...data },
        {
          headers: headers,
        }
      );
    }
  }
}

export default ApiClient;

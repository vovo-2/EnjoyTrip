<template>
  <div class="file-upload-component" @click="setting">
    <p class="desc">
      *업로드할 이미지를 PC에서 선택하거나 아래 공간으로 드래그 해주세요.
    </p>

    <button @click.stop="$refs.fileReader.click()">찾아보기</button>
    <p class="ext mb-5">업로드 가능 : JPG,JPEG,PNG</p>

    <input
      type="file"
      class="file-reader"
      ref="fileReader"
      @change="uploadFile"
    />
  </div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref, defineProps } from "vue";
const props = defineProps({
  callback: {
    type: Function,
    default: () => {},
  },
});
const fileReader = ref(null);
const clearFile = () => {
  fileReader.value = "";
};
const setting = () => {
  this.clearFile();
  getCurrentInstance().ctx.$refs.fileReader.click();
};
const dropFile = (event) => {
  event.preventDefault();
  upload(event.dataTransfer.files[0]);
};
const uploadFile = (event) => {
  if (event.target.files) {
    const file = event.target.files[0];
    upload(file);
  }
};
const upload = async (file) => {
  const validImgTypes = ["image/png", "image/jpg", "image/jpeg"];
  if (!validImgTypes.includes(file.type.toLowerCase())) {
    clearFile();
    alert("png, jpg, jpeg 파일을 선택해주세요.");
    return null;
  }
  callback({
    file,
  });
};
const preventDefault = (e) => {
  e.preventDefault();
};
onMounted(() => {
  window.addEventListener("dragleave", preventDefault);
  window.addEventListener("dragover", preventDefault);
  window.addEventListener("drop", dropFile);
});
onBeforeUnmount(() => {
  window.removeEventListener("dragleave", preventDefault);
  window.removeEventListener("dragover", preventDefault);
  window.removeEventListener("drop", dropFile);
});
</script>

<style lang="scss" scoped>
.file-upload-component {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  border: 1px solid $gray3;
  border-radius: 4px;
  background: #ffffff;
}
.file-upload-component .desc {
  margin-top: 55px;
  font-size: 16px;
  font-weight: 300;
  color: #999999;
}
.file-upload-component img {
  width: 50px;
  height: 55px;
  margin-top: 30px;
}
.file-upload-component .drop {
  margin-top: 23px;
  font-size: 14px;
  font-weight: 300;
  color: #333333;
}
.file-upload-component .or {
  font-size: 14px;
  font-weight: 300;
  color: #9e9e9e;
}
.file-upload-component button {
  margin-top: 12px;
  width: 157px;
  height: 42px;
  border-radius: 4px;
  background-color: #ff791c;
  font-size: 14px;
  font-weight: 500;
  color: #ffffff;
}
.file-upload-component .ext {
  margin-top: 31px;
  font-size: 14px;
  font-weight: 300;
  color: #c4c4c4;
}
.file-upload-component .file-reader {
  width: 0;
  height: 0;
  opacity: 0;
}
</style>

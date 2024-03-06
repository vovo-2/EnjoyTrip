<script setup>
import { ref, watch, onMounted, defineProps } from "vue";

const props = defineProps(["position"]);
const markers = ref([]);
let map;
const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
function displayMarker(locPosition) {
  deleteMarkers(); // Remove existing markers
  // Create and display a new marker
  let marker = new kakao.maps.Marker({
    map: map,
    position: locPosition,
  });

  var infoWindow = new kakao.maps.InfoWindow();

  infoWindow.open(map, marker);
  map.panTo(locPosition);
}

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.460701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
};

watch(
  () => props.position,
  (newVal) => {
    // Watch for changes in the prop and update marker position accordingly
    if (newVal) {
      const lat = newVal.lat,
        lon = newVal.lon;
      const newLocPosition = new kakao.maps.LatLng(lat, lon);
      deleteMarkers();
      displayMarker(newLocPosition);
    }
  },
  { deep: true }
);
</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 700px;
}
</style>

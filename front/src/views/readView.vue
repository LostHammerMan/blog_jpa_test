<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  }
});

const moveToEdit = () => {
  router.push({name: "edit", params:{postId: props.postId}})
}

// 초기화 코드
const post = ref({
  id: 0,
  title: "",
  content: ""
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  })
})
</script>

<template>
  <el-container style="flex-direction: column">
    <el-main>{{ post.title }}</el-main>
    <div class="m-lg-3">
      <el-card>{{ post.content }}</el-card>
    </div>
    <el-button type="primary" class="m-lg-3" @click="moveToEdit()">수정</el-button>
  </el-container>
</template>


<style scoped>

</style>
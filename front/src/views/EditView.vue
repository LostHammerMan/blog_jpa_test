<script setup>
  import {useRouter} from "vue-router";
  import {ref} from "vue";
  import axios from "axios";

  const router = useRouter();

  const post = ref({
    id: 0,
    title: "",
    content: ""
  });

  const props = defineProps({
    postId: {
      type: [Number, String],
      require: true,
    }
  });

  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });

  const edit = () => {
    axios.patch(`/api/posts/${props.postId}`, post.value).then(() => {
      router.replace({name: "home"})
    })
  }

</script>


<template>
  <div class="d-flex item" style="flex-direction: column">
    <div>
      <el-input v-model="post.title"></el-input>
    </div>

    <div class="mt-3">
      <el-input type="textarea"  v-model="post.content"></el-input>
    </div>

    <div class="mt-3">
      <el-button type="primary" @click="edit()">수정 완료</el-button>
    </div>
  </div>
</template>


<style scoped>

</style>
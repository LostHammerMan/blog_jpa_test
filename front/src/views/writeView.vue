<script setup lang="ts">

import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";


const count = ref(0); // 반응형 변수 만들기
const title = ref("")
const content = ref("")
const router = useRouter()

// 서버로 데이터를 요청할 때 axios 라이브러리 활용
const write = function (){
  axios.post("/api/posts", { // 프록시를 두고 연결 > vite.config.ts
    title : title.value,
    content : content.value
  })
      .then(() => {
        router.replace({name: "home"});
      })

}


</script>

<template>
  <div class="d-flex item" style="flex-direction: column">
    <div>
    <el-input placeholder="제목 입력" v-model="title"></el-input>
    </div>

    <div class="mt-3">
    <el-input type="textarea" placeholder="내용 입력" v-model="content"></el-input>
    </div>

    <div class="mt-3">
    <el-button type="primary" @click="write">글 작성</el-button>
    </div>
  </div>
</template>


<style>

</style>
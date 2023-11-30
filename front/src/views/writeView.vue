<script setup lang="ts">
import {ref} from "vue";

const count = ref(0); // 반응형 변수 만들기
const title = ref("")
const content = ref("")

const write = function (){
  console.log(title, content)
}
</script>

<template>
  <!--  <p>안녕하세요</p> <button @click="count += 1">버튼을 눌러주세요</button>-->
  <!--  <p>{{count}}</p>-->
  <div class="d-flex mb-2">
    <el-input placeholder="제목 입력" v-model="title"></el-input>
  </div>

  <div class="">
    <div class="d-flex mb-3">
      <el-input type="textarea" placeholder="내용 입력" v-model="content"></el-input>
    </div>
    <div>
      <el-button type="primary" @click="write">글 작성</el-button>
    </div>
  </div>
</template>


<style>

</style>
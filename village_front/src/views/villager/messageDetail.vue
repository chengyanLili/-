<template>
   
</template>

<script setup>
import bus from '@/utils/mitt.js'
import { onMounted } from 'vue';
onMounted(()=>{
    bus.on('msg',msg)
    console.log('#',msg);
})

</script>

<style>

</style>
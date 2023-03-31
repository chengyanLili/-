<template>
        <Layout :column="2">
                <template #myPolicy-0>
                        <p>决策1</p>
                </template>
                <template #myPolicy-1>
                        <p>决策2</p>
                </template>
                <template #myPolicy-2>
                        <p>决策3</p>
                </template>
        </Layout>
</template>
      
<script setup>
import Layout from '@/views/villagerLaout/Layout.vue';

</script>
      
<style></style>
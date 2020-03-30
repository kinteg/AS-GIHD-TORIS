<template>
    <div>
        <router-view  style="padding:30px; border-radius:5px; "/>
    </div>
</template>

<script>
    export default {
        name: "source",
        beforeRouteUpdate (to, from, next) {
            alert ("ll");
            next();
        }
    }
</script>

<style scoped>

</style>
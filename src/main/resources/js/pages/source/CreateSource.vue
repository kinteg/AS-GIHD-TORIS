<template>
    <div>
        <source-create/>
    </div>
</template>

<script>

    import SourceCreate from "../../components/source/sourceCreate.vue";
    export default {
        name: "CreateSource",
        components: {SourceCreate},
        beforeRouteUpdate (to, from, next) {
            alert ("ll");
            next();
        }
    }
</script>

<style scoped>

</style>
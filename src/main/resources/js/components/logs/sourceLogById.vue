<template>
    <div>

    </div>
</template>

<script>
    export default {
        name: "sourceLogById",
        data() {
            return{
                sourceLogId:"",
            }
        },

        methods: {

        },

        mounted() {
            this.sourceLogId = this.$route.params.id;

        }
    }
</script>

<style scoped>

</style>
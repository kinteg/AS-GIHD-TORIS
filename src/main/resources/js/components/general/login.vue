<template>
    <div>
        <p>rtr</p>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    import {getToken} from "../../modules/auth";

    export default {
        name: "login",
        data(){
            return{

            }
        },
        methods:{

        },

        mounted() {
            console.log(getToken());

        }
    }
</script>

<style scoped>

</style>
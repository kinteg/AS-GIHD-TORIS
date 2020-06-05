<template>
    <div>
        <p>rtr</p>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";
    import {getToken} from "../../modules/auth";
    import router from "../../router/router";

    export default {
        name: "login",
        data(){
            return{

            }
        },
        methods:{

        },

        mounted() {
            AXIOS.get("user/acceptToken/" + getToken()).then(response => {
                router.push({name:"show"})
            });
        }
    }
</script>

<style scoped>

</style>
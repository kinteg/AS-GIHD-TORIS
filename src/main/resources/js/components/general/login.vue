<template>
    <div>
        <p>rtr</p>
    </div>
</template>

<script>
    import {AXIOS} from "../../AXIOS/http-common";

    export default {
        name: "login",
        data(){
            return{

            }
        },
        methods:{

        },

        mounted() {
            // let formData = new FormData();
            // formData.append("SystemID","urn-eis-toris-gihd-uzd-2020");
            // formData.append("Authorization","e9db1ecb-2a0d-4f78-b68e-0fb8ca063091");
            // TORIS.userProfile(formData);
            // AXIOS.get('test.toris.gov.spb.ru/userinfo',{
            //     headers: {"Content-type: application/json",
            //              Authorization:"urn-eis-toris-gihd-uzd-2020",
            //              ""}
            // })

            AXIOS({
                method: 'get',
                url: 'test.toris.gov.spb.ru/api/ personal/user/profile/',
                headers:{
                    'Content-Type': 'application/json',
                    'Authorization': 'e9db1ecb-2a0d-4f78-b68e-0fb8ca063091',
                    'SystemID': 'urn-eis-toris-gihd-uzd-2020'
                }
            })
        }
    }
</script>

<style scoped>

</style>
<template>
    <div>
        <v-list two-line v-if="list.length > 0">
            <v-list-item-group 
                    v-model="selected" 
                    color="primary"
                    @change="select"
            >
                <v-list-item v-for="(item, idx) in list" :key="idx">
                    <template v-slot:default="{ active }">
                        <v-list-item-avatar color="primary-darker-1">
                        </v-list-item-avatar>
                        
                        <v-list-item-content>
                            <v-list-item-title>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                MenuId :  {{item.menuId }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                MenuName :  {{item.menuName }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Description :  {{item.description }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Price :  {{item.price }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                IsAvailable :  {{item.isAvailable }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Category :  {{item.category }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Discount :  {{item.discount }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                TodayOrderCount :  {{item.todayOrderCount }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                AccOrderCount :  {{item.accOrderCount }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                IsHot :  {{item.isHot }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                ImageUrl :  {{item.imageUrl }}
                            </v-list-item-subtitle>
                        </v-list-item-content>

                        <v-list-item-action>
                            <v-checkbox :input-value="active" color="primary-darker-1"></v-checkbox>
                        </v-list-item-action>
                    </template>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </div>
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'MenuMenuPicker',
        props: {
            value: [String, Object, Array, Number, Boolean],
        },
        data: () => ({
            list: [],
            selected: null,
        }),
        async created() {
            var me = this;
            var temp = await axios.get(axios.fixUrl('/menus'))
            if(temp.data) {
                me.list = temp.data._embedded.menus;
            }

            if(me.value && typeof me.value == "object" && Object.values(me.value)[0]) {
                var id = Object.values(me.value)[0];
                var tmpValue = await axios.get(axios.fixUrl('/menus/' + id))
                if(tmpValue.data) {
                    var val = tmpValue.data
                    me.list.forEach(function(item, idx) {
                        if(item.name == val.name) {
                            me.selected = idx
                        }
                    })
                }
            }
        },
        methods: {
            select(val) {
                var obj = {}
                if(val != undefined) {
                    var arr = this.list[val]._links.self.href.split('/');
                    obj['menuId'] = arr[4]; 
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    this.$emit('selected', obj);
                }
            },
        },
    };
</script>


<template>
  <div
    class="table__wrapper border-box hide-scrollbar"
    :class="{ mini }"
    :style="variables"
  >
    <table>
      <thead>
        <tr>
          <template v-for="header in headers">
            <th v-if="header.value === 'checked'" :key="header.value">
              <p-checkbox v-model="check" @input="checkAll" :key="compKey" />
            </th>
            <slot
              :name="`${header.value}Header`"
              v-else-if="$scopedSlots[`${header.value}Header`]"
            />
            <th :key="header.value" v-html="header.text" v-else />
          </template>
        </tr>
      </thead>
      <tbody>
        <tr v-if="!items.length" class="no-data">
          <td>
            <v-progress-circular
              v-if="loading"
              class="mr-2"
              indeterminate
              color="black"
              :size="16"
              width="2"
            />{{ loading ? "데이터를 불러오는 중입니다." : emptyMsg }}
          </td>
        </tr>
        <template v-else>
          <tr
            v-for="(item, index) in items"
            :key="index"
            @click="onRowClick(item)"
            :class="{ checked: item.checked }"
          >
            <td v-for="key in keys" :key="key">
              <slot
                :name="key"
                :item="item"
                v-if="$scopedSlots[key]"
              /><template v-else>{{ item[key] }}</template>
            </td>
          </tr>
        </template>
      </tbody>
    </table>
  </div>
</template>

<script>
import { v4 as uuid } from "uuid";
export default {
  name: "PoinUiTable",
  props: {
    headers: {
      type: Array,
      default: () => [],
    },
    items: {
      type: Array,
      default: () => [],
    },
    gridTemplateColumns: {
      type: String,
      default: "",
    },
    emptyMsg: {
      type: String,
      default: "등록된 데이터가 없습니다.",
    },
    loading: {
      type: Boolean,
      default: false,
    },
    mini: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      check: false,
      compKey: uuid(),
    };
  },
  computed: {
    variables() {
      const basic = this.keys.includes("checked")
        ? `48px repeat(${this.keys?.length - 1 || 0}, 1fr)`
        : `repeat(${this.keys?.length || 0}, 1fr)`;
      return {
        "--grid-template-columns":
          this.items?.length > 0 ? this.gridTemplateColumns || basic : basic,
      };
    },
    keys() {
      return this.headers?.map((header) => header.value);
    },
  },
  watch: {
    items: {
      deep: true,
      handler(items) {
        if (items?.length > 0) {
          const allChecked = items.every(
            (item) => item.checked || item.disabled
          );
          const allDisabled = items.every((item) => item.disabled);
          if (!allDisabled && allChecked) {
            this.check = true;
            return;
          }
        }
        this.check = false;
        this.compKey = uuid();
      },
    },
  },
  methods: {
    onRowClick(item) {
      this.$emit("click:row", item);
    },
    checkAll(val) {
      this.$emit("checkAll", val);
    },
  },
};
</script>

<style lang="scss" scoped>
$grid-template-columns: var(--grid-template-columns);

.table__wrapper {
  width: 100%;
  overflow-x: auto;
  white-space: nowrap;
  table {
    width: 100%;
    thead {
      border-top: 3px solid $darkGreen;
      border-bottom: 2px solid #b8c3c8;
      background-color: #edf1f5;
      vertical-align: middle;
      th {
        color: $gray8;
        font-weight: 500;
        font-size: 14px;
        text-align: left;
        vertical-align: middle;
        padding: 0 12px;
        height: 48px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
      }
    }
    tbody {
      tr {
        border-bottom: 1px solid #dedede;
      }
      td {
        font-weight: 400;
        font-size: 14px;
        text-align: left;
        vertical-align: middle;
        display: flex;
        align-items: center;
        white-space: pre-wrap;
        color: $black;
        padding: 0 12px;
        line-height: 18px;
        height: 52px;
      }
    }
    tr {
      display: grid;
      grid-template-columns: $grid-template-columns;
    }
    .no-data {
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .checked {
      background-color: $primary100;
    }
  }
  &.mini {
    table {
      thead {
        border-top: 1px solid $gray3;
        border-bottom: 1px solid $gray3;
        background-color: $white;
        vertical-align: middle;
      }
      tbody {
        tr {
          border-bottom: none;
          background-color: $white;
          &.checked {
            background-color: $primary100;
          }
        }
        td {
          color: $black;
          padding: 0 12px;
          line-height: 18px;
          height: 56px;
        }
      }
    }
  }
}
</style>

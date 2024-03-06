import colors from 'vuetify/lib/util/colors'

const customColors = {
  primary: Object.freeze({
    base: '#f55e42',
    lighten4: '#feeeeb',
    lighten3: '#fddfd9',
    lighten2: '#faaea0',
    lighten1: '#f77e67'
  }),
  secondary: Object.freeze({
    base: '#083D45',
    lighten4: '#E5EBEC',
    lighten3: '#cdd8da',
    lighten2: '#839DA1',
    lighten1: '#39636A'
  }),
  darkGreen: '#083D45',
  coral: '#f55e42',
  yellow: '#F5DF4D',
  orange: '#FF6900',
  navy: '#292A4D',
  cancel: '#929AA5',
  lightGreen1: '#cfeee9',
  lightGreen2: '#a2cbc4',
  lightGreen3: '#47867b',
  error: '#E03028',
  caution: '#F9A825',
  success: '#2FB916',
  white: '#FFFFFF',
  black: '#000000',
  primary100: '#feeeeb',
  primary300: '#fddfd9',
  primary500: '#faaea0',
  primary800: '#f77e67',
  secondary100: '#E5EBEC',
  secondary300: '#cdd8da',
  secondary500: '#839DA1',
  secondary800: '#39636A',
  gray: Object.freeze({
    base: '#BEC1C7',
    lighten4: '#F9FAFC',
    lighten3: '#EFF1F4',
    lighten2: '#E4E6EA',
    lighten1: '#D5D8DC',
    darken1: '#9DA0A8',
    darken2: '#7F828C',
    darken3: '#5A5E6A',
    darken4: '#3B3F4A'
  }),
  greenBoxColor: '#cfeee9',
  greenBoxBorderColor: '#a2cbc4',
  greenBoxFontColor: '#47867b'
}

export default {
  ...colors,
  ...customColors
}

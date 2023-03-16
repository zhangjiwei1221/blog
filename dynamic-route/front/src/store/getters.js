const getters = {
  token: state => state.user.token,
  name: state => state.user.name,
  permission_routes: state => state.permission.routes
}
export default getters

/**
 * check the authorities
 * @param loginUser current login user type.
 * @param needAccess the authority level needed.
 * @return boolean whether the user has the accessing permission.
 *
 */
import ACCESS_ENUM from "./accessEnum";

const checkAccess = (loginUser: any, needAccess = ACCESS_ENUM.NOT_LOGIN) => {
  // get the login type of the current user, if there are no loginUser, get NOT_LOGIN by default.
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN;

  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  // if the user need to login first.
  if (needAccess === ACCESS_ENUM.USER) {
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
  }
  // if the user need the admin permission
  if (needAccess === ACCESS_ENUM.ADMIN) {
    //if the user does not have the admin permission
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false;
    }
  }

  return true;
};

export default checkAccess;

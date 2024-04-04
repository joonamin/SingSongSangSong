import { configureStore } from "@reduxjs/toolkit";

import userSlice from "./userSlice";
import musicSlice from "./musicSlice";

const store = configureStore({
  reducer: {
    user: userSlice.reducer,
    music: musicSlice.reducer,
  },
});

export default store;
export type RootState = ReturnType<typeof store.getState>;
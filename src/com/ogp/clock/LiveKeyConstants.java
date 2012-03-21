/*
Copyright (c) 2011, Sony Ericsson Mobile Communications AB

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the Sony Ericsson Mobile Communications AB nor the names
  of its contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.ogp.clock;

/**
 * Constants for the LiveKey™ Intent.
 */

public final class LiveKeyConstants {

    private LiveKeyConstants() {
        // empty to disallow instantiation
    }

    /**
     * Integer ID of the pressed button, 0 or greater.
     */
    public static final String EXTRA_ID = "com.sonyericsson.extras.livekey.id";

    /**
     * Integer key state, see EXTRA_STATE_PRESSED and EXTRA_STATE_RELEASED.
     */
    public static final String EXTRA_STATE = "com.sonyericsson.extras.livekey.state";

    /**
     * Long timestamp in the SystemClock.elapsedRealtime() time base.
     */
    public static final String EXTRA_TIMESTAMP = "com.sonyericsson.extras.livekey.timestamp";

    /**
     * Action String for the LiveKey™ Intent.
     */
    public static final String ACTION = "com.sonyericsson.extras.livekey";

    /**
     * Integer key state constant for a pressed state.
     */
    public static final int EXTRA_STATE_PRESSED = 0;

    /**
     * Integer key state constant for a released state.
     */
    public static final int EXTRA_STATE_RELEASED = 1;
}
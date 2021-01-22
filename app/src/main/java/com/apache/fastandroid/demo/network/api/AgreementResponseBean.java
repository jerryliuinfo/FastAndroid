package com.apache.fastandroid.demo.network.api;

/**
 * Created by Jerry on 2021/1/20.
 */
public class AgreementResponseBean extends BaseResponse {

    public Data data;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"data\":")
                .append(data);
        sb.append('}');
        return sb.toString();
    }

    public static class Data{
        public String userAgreementLink; //"http://kids.test.edu.tcljd.com/api/agreement/user/DNwjMixgTH"
        public String privacyPolicyLink; //"http://kids.test.edu.tcljd.com/api/agreement/privacy/OEZySzaaeJ"

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"userAgreementLink\":\"")
                    .append(userAgreementLink).append('\"');
            sb.append(",\"privacyPolicyLink\":\"")
                    .append(privacyPolicyLink).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }
}

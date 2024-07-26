package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.jjbfather.jjjqsrt2go2gorestkakaovx.MainMiddleService.dbInit;

/**
 * Created by BCS_RTBS_JJFATHER on 2015-10-15.
 */
public class SaleCartAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<TemporarySaleCart> mArSrc;
    int layout;
    TemporarySaleCart mTempSaleCart;

    String tempKitchenPrinted = "";
    String tempTextColor = "";

    // 멀티선택관련 추가
    // 체크박스 저장함수
    private ViewHolder viewHolder = null;

    public SaleCartAdapter(Context context, int alayout, ArrayList<TemporarySaleCart> aarSrc) {
        this.mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mArSrc = aarSrc;
        this.layout = alayout;

//        ///
//        if (GlobalMemberValues.is_customerMain){
//            this.mArSrc.clear();
//
//            this.mArSrc.addAll(aarSrc);
//
//            notifyDataSetChanged();
//        }
    }

    @Override
    public int getCount() {
        return mArSrc.size();
    }

    @Override
    public String getItem(int position) {
        //return mArSrc.get(position).sHoldCode;
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;

        final TemporarySaleCart temporarySaleCart = mArSrc.get(position);

        String itemTextColor = GlobalMemberValues.STR_MAINSALECART_TEXTCOLOR_SERVICE;
        String itemEachValue = mArSrc.get(position).mSPriceBalAmount;
        final String itemSaveType = mArSrc.get(position).mSaveType;

        final String itemAllEachType = mArSrc.get(position).selectedDcExtraAllEach;
        final String itemHoldCode = mArSrc.get(position).mHoldCode;

        GlobalMemberValues.logWrite("MultiTestLog2", "-------------------------------------------------" + "\n");
        GlobalMemberValues.logWrite("MultiTestLog2", "itemSaveType : " + itemSaveType + "\n");

        TextView discountExtraDeleteButton = null;

        // 07.18.2022 - add pay for cash, card
        CheckBox tempChkBox = null;

        if (convertView == null) {
            convertView = mInflater.inflate(layout, parent, false);

            discountExtraDeleteButton = (TextView)convertView.findViewById(R.id.discountExtraDeleteButton);
            discountExtraDeleteButton.setVisibility(View.GONE);


            CheckBox checkBoxView = (CheckBox)convertView.findViewById(R.id.mainSaleCartListCheckBox);

            // 07.18.2022 - add pay for cash, card
            tempChkBox = checkBoxView;

            if (!(itemSaveType == "8" || itemSaveType == "9")) {

                discountExtraDeleteButton.setVisibility(View.GONE);

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    // 멀티선택관련 추가
                    // 체크박스 저장함수 생성 및 체크박스뷰 생성
                    checkBoxView.setVisibility(View.VISIBLE);
                    viewHolder = new ViewHolder();
                    viewHolder.cBox = checkBoxView;
                    convertView.setTag(viewHolder);

                    // jihun add
                    viewHolder.ln_background = (LinearLayout)convertView.findViewById(R.id.mainSaleCart_Background);

                    GlobalMemberValues.logWrite("MultiTestLog2", "여기실행1" + "\n");
                } else {
                    checkBoxView.setVisibility(View.GONE);

                    GlobalMemberValues.logWrite("MultiTestLog2", "여기실행2" + "\n");
                }
            } else {
                checkBoxView.setVisibility(View.GONE);
                GlobalMemberValues.logWrite("MultiTestLog2", "여기실행3" + "\n");

                // 할인/추가 discount / extra 일 경우 버튼이 보이도록
                discountExtraDeleteButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (!(itemSaveType == "8" || itemSaveType == "9")) {
                discountExtraDeleteButton = (TextView) convertView.findViewById(R.id.discountExtraDeleteButton);
                discountExtraDeleteButton.setVisibility(View.GONE);

                if (GlobalMemberValues.isMultiCheckOnCart()) {
                    // 멀티선택관련 추가
                    // 체크박스 저장함수에 태그저장
                    viewHolder = (ViewHolder)convertView.getTag();
                    GlobalMemberValues.logWrite("MultiTestLog2", "여기실행4" + "\n");

                    CheckBox checkBoxView = (CheckBox)convertView.findViewById(R.id.mainSaleCartListCheckBox);
                    checkBoxView.setVisibility(View.VISIBLE);

                    // 07.18.2022 - add pay for cash, card
                    tempChkBox = checkBoxView;
                }
            } else {
                CheckBox checkBoxView = (CheckBox)convertView.findViewById(R.id.mainSaleCartListCheckBox);
                checkBoxView.setVisibility(View.GONE);

                // 07.18.2022 - add pay for cash, card
                tempChkBox = checkBoxView;

                GlobalMemberValues.logWrite("MultiTestLog2", "여기실행5" + "\n");

                // 할인/추가 discount / extra 일 경우 버튼이 보이도록

                discountExtraDeleteButton = (TextView) convertView.findViewById(R.id.discountExtraDeleteButton);
                discountExtraDeleteButton.setVisibility(View.VISIBLE);
            }
        }

        // 할인/추가 discount / extra 버튼 관련
        if (itemSaveType == "8" || itemSaveType == "9") {
            final View finalConvertView = convertView;
            discountExtraDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (temporarySaleCart != null) {
                        if (!MainActivity.mActivity.isFinishing()) {
                            // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                            if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                            // ------------------------------------------------------------
                            new AlertDialog.Builder(MainActivity.mContext)
                                    .setTitle("Discount / Extra")
                                    .setMessage("Are you sure to cancel discount/extra?")
                                    .setNegativeButton("No", null)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String vParentSaleCartIdx = temporarySaleCart.selectedDcExtraParentTempSaleCartIdx;
                                            TemporarySaleCart vParentTemporarySaleCart = temporarySaleCart.selectedDcExtraParentTemporarySaleCart;

                                            //finalConvertView.setVisibility(View.GONE);
                                            Discount.cancelDiscountExtra(vParentTemporarySaleCart, vParentSaleCartIdx, position, itemAllEachType, itemHoldCode);
                                            v.setVisibility(View.GONE);
                                        }
                                    }).show();
                        }
                    }
                }
            });
        }

        // kitchen memo 있는 부분
        LinearLayout kitchenMemoLn = null;
        kitchenMemoLn = (LinearLayout) convertView.findViewById(R.id.mainSaleCartListLinearLayout3);


        int iconImg = 0;
        switch (itemSaveType) {
            case "1" : {
                itemTextColor = GlobalMemberValues.STR_MAINSALECART_TEXTCOLOR_PRODUCT;
                iconImg = R.drawable.aa_images_icon_product;
                break;
            }
            case "2" : {
                itemTextColor = GlobalMemberValues.STR_MAINSALECART_TEXTCOLOR_GIFTCARD;
                iconImg = R.drawable.aa_images_icon_giftcard;
                break;
            }
            case "8" : {
                //itemEachValue = mArSrc.get(position).mSPriceBalAmount;
                itemTextColor = GlobalMemberValues.SALECART_DISCOUNTBACKGROUNDCOLOR;
                iconImg = 0;

                break;
            }
            case "9" : {
                //itemEachValue = mArSrc.get(position).mSPriceBalAmount;
                itemTextColor = GlobalMemberValues.SALECART_EXTRABACKGROUNDCOLOR;
                iconImg = 0;

                break;
            }
        }

        float tempFontSize1 = GlobalMemberValues.BASICITEMLISTTEXTSIZE + 4;
        float tempFontSize2 = 14;
        if (GlobalMemberValues.isBigScreen()) {
            tempFontSize1 = GlobalMemberValues.BASICITEMLISTTEXTSIZE2 + 6;
            tempFontSize2 = GlobalMemberValues.BASICITEMLISTTEXTSIZE;
        }

        if (GlobalMemberValues.isDeviceSunmiFromDB()){
            tempFontSize1 = tempFontSize1 - 3;
            tempFontSize2 = tempFontSize2 - 3;
        }

        if (GlobalMemberValues.isDevicePAXFromDB()){
            tempFontSize1 = tempFontSize1 - 3;
            tempFontSize2 = tempFontSize2 - 3;
        }

        if (GlobalMemberValues.mDeviceTabletPC){
            tempFontSize1 = tempFontSize1 - 6;
            tempFontSize2 = tempFontSize2 - 6;
        }

        // 위의 설정 사용하지 않고 기본색상 사용시에.......................
        itemTextColor = "#ffffff";
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            itemTextColor = "#3e3d42";
        }

        // bill pay 이고, 타입이 menu split 일 경우
        if (GlobalMemberValues.isPaymentByBills && GlobalMemberValues.mBillSplitType.equals("0")) {
            viewHolder.ln_background.setVisibility(View.GONE);
//            itemTextColor = "#39445b";
            String tempCartIdx[] = GlobalMemberValues.mCartIdxsOnBillPay.split(",");
            for (int i = 0; i < tempCartIdx.length; i++) {
                String vCartIdx = GlobalMemberValues.getReplaceText(tempCartIdx[i], " ", "");
                if (mArSrc.get(position).tempSaleCartIdx.equals(vCartIdx)) {
//                    itemTextColor = "#ffffff";
                    viewHolder.ln_background.setVisibility(View.VISIBLE);
                }
            }
        }

        final String tempSvcIdx = mArSrc.get(position).mSvcidx;

        String tempDcExParentIdx = mArSrc.get(position).selectedDcExtraParentTempSaleCartIdx;
        String tempSvcName = mArSrc.get(position).mSvcName.toUpperCase();
        if (GlobalMemberValues.isStrEmpty(tempDcExParentIdx)
                && tempSvcName.indexOf("ALL DISCOUNT") == -1 && tempSvcName.indexOf("ALL EXTRA") == -1 ) {
        } else {
            if (tempSvcName.indexOf("DISCOUNT") > -1) {
                itemTextColor = "#f71139";
            }
            if (tempSvcName.indexOf("EXTRA") > -1) {
                itemTextColor = "#0c5bef";
            }
        }

        TextView tvCategoryColor = (TextView)convertView.findViewById(R.id.mainSaleCartListCategoryColor);
        if (!GlobalMemberValues.isStrEmpty(mArSrc.get(position).mCategoryColor)) {
            tvCategoryColor.setBackgroundColor(Color.parseColor(mArSrc.get(position).mCategoryColor));
        } else {
            tvCategoryColor.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        TextView tvTaxExempt = (TextView)convertView.findViewById(R.id.mainSaleCartListTaxExempt);
        if (!GlobalMemberValues.isStrEmpty(mArSrc.get(position).mTaxExempt)
                && (mArSrc.get(position).mTaxExempt == "Y" || mArSrc.get(position).mTaxExempt.equals("Y"))) {
            tvTaxExempt.setTextColor(Color.parseColor("#36bdff"));
            tvTaxExempt.setText("T/E");
            tvTaxExempt.setTextSize(tempFontSize1);
            // LITE 버전 관련
            if (GlobalMemberValues.isLiteVersion()) {
                tvTaxExempt.setTextSize(12);
            }
        } else {
            tvTaxExempt.setText("");
        }

        TextView tvEmp = (TextView)convertView.findViewById(R.id.mainSaleCartListEmp);
        tvEmp.setTextColor(Color.parseColor(itemTextColor));
        if (!(itemSaveType == "8" || itemSaveType == "9")) {
            tvEmp.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            tvEmp.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
        tvEmp.setText(mArSrc.get(position).mEmpName);
        tvEmp.setTextSize(tempFontSize1);
        //tvEmp.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        GlobalMemberValues.logWrite("salecartadapter", "textColor : " + itemTextColor);

        TextView tvMenuName = (TextView)convertView.findViewById(R.id.mainSaleCartListMenuName);
        tvMenuName.setTextColor(Color.parseColor(itemTextColor));
        if (!(itemSaveType == "8" || itemSaveType == "9")) {
            tvMenuName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            tvMenuName.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
        if (iconImg > 0 || mArSrc.get(position).mQuickSaleYN.equals("Y") || mArSrc.get(position).mQuickSaleYN == "Y") {
            if (mArSrc.get(position).mQuickSaleYN.equals("Y") || mArSrc.get(position).mQuickSaleYN == "Y") {
                tvMenuName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.aa_images_icon_quick, 0, 0, 0);
                tvMenuName.setCompoundDrawablePadding(10);
            } else {
                tvMenuName.setCompoundDrawablesWithIntrinsicBounds(iconImg, 0, 0, 0);
                tvMenuName.setCompoundDrawablePadding(10);
            }
        } else {
            tvMenuName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        tvMenuName.setText(mArSrc.get(position).mSvcName);
        tvMenuName.setTextSize(tempFontSize1);
        //tvMenuName.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));

        TextView tvQty = (TextView)convertView.findViewById(R.id.mainSaleCartListQty);
        tvQty.setTextColor(Color.parseColor(itemTextColor));
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            tvQty.setTextColor(Color.parseColor("#a4a2a9"));
        }
        if (!(itemSaveType == "8" || itemSaveType == "9")) {
            tvQty.setText(mArSrc.get(position).mSQty);
        } else {
            tvQty.setText("--");
        }
        tvQty.setTextSize(tempFontSize1);
        if (!GlobalMemberValues.isLiteVersion()) {
            tvQty.setBackgroundResource(R.drawable.roundlayout_background_qty);
        }
        //tvQty.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));
        // Lite 버전 관련
        if (GlobalMemberValues.isLiteVersion()) {
            tvQty.setTextSize(tempFontSize1 - 2);
        }

        tvQty.setVisibility(View.VISIBLE);


        /** DC / Extra 가 있을 경우 보여지는 값을 원래가격 또는 할인이 적용된 가격으로 보여주기 **/
        GlobalMemberValues.logWrite("SHOWCOSTAFTERDCEXTRALOG", "GLOBAL_SHOWCOSTAFTERDCEXTRA : " + GlobalMemberValues.GLOBAL_SHOWCOSTAFTERDCEXTRA + "\n");
        if (GlobalMemberValues.GLOBAL_SHOWCOSTAFTERDCEXTRA == "N" || GlobalMemberValues.GLOBAL_SHOWCOSTAFTERDCEXTRA.equals("N")) {
            if (!(itemSaveType == "8" || itemSaveType == "9")) {
                /**
                 double itemEachValueDCExtra = GlobalMemberValues.getDoubleAtString(itemEachValue);

                 // Each DC / Extra -----------------------------------------------------------------------
                 String tempDCExtraType = mArSrc.get(position).selectedDcExtraType;
                 String tempDCExtra = mArSrc.get(position).selectedDcExtraPrice;
                 tempDCExtra = GlobalMemberValues.getReplaceText(tempDCExtra, "-", "");
                 tempDCExtra = GlobalMemberValues.getReplaceText(tempDCExtra, "+", "");

                 double dblDCExtra = GlobalMemberValues.getDoubleAtString(tempDCExtra);

                 if (tempDCExtraType.equals("EX")) {
                 itemEachValueDCExtra = GlobalMemberValues.getDoubleAtString(itemEachValue) - dblDCExtra;
                 }
                 if (tempDCExtraType.equals("DC")) {
                 itemEachValueDCExtra = GlobalMemberValues.getDoubleAtString(itemEachValue) + dblDCExtra;
                 }
                 // ---------------------------------------------------------------------------------------

                 // All DC / Extra -----------------------------------------------------------------------
                 String tempAllDCExtraType = mArSrc.get(position).allDcExtraType;
                 String tempAllDCExtra = mArSrc.get(position).allDcExtraPrice;
                 tempAllDCExtra = GlobalMemberValues.getReplaceText(tempAllDCExtra, "-", "");
                 tempAllDCExtra = GlobalMemberValues.getReplaceText(tempAllDCExtra, "+", "");

                 double dblAllDCExtra = GlobalMemberValues.getDoubleAtString(tempAllDCExtra);

                 if (tempAllDCExtraType.equals("EX")) {
                 itemEachValueDCExtra = GlobalMemberValues.getDoubleAtString(itemEachValue) - dblAllDCExtra;
                 }
                 if (tempAllDCExtraType.equals("DC")) {
                 itemEachValueDCExtra = GlobalMemberValues.getDoubleAtString(itemEachValue) + dblAllDCExtra;
                 }
                 // ---------------------------------------------------------------------------------------

                 itemEachValue = itemEachValueDCExtra + "";
                 **/

                double itemStaticMSPrice = GlobalMemberValues.getDoubleAtString(mArSrc.get(position).staticMSPrice);
                double dblEachValue = itemStaticMSPrice * GlobalMemberValues.getDoubleAtString(mArSrc.get(position).mSQty);

                itemEachValue = dblEachValue + "";
            }
        }

        if (itemSaveType == "8" || itemSaveType == "9") {
            if (kitchenMemoLn != null) {
                kitchenMemoLn.setVisibility(View.GONE);
            }
        }

        // Common Gratuity 관련
        LinearLayout kitchenprintingmsgLn = (LinearLayout) convertView.findViewById(R.id.mainSaleCartListLinearLayout4);
        kitchenprintingmsgLn.setVisibility(View.VISIBLE);

        /************************************************************************************/

        String dollarStr = "";
        if (GlobalMemberValues.ISDISPLAYPRICEDOLLAR) {
            dollarStr = "$";
        }

        TextView tvEach = (TextView)convertView.findViewById(R.id.mainSaleCartListEach);
        tvEach.setTextColor(Color.parseColor("#36bdff"));
        if (!(itemSaveType == "8" || itemSaveType == "9")) {
            tvEach.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            tvEach.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
        tvEach.setText(dollarStr + GlobalMemberValues.getCommaStringForDouble(dollarStr + itemEachValue));
        tvEach.setTextSize(tempFontSize1);
        //tvEach.setTextColor(Color.parseColor(GlobalMemberValues.BASICCOMMONLISTVIEWTEXT));


        /** 옵션 & 추가사항 **************************************************************************************/
        // 옵션&추가사항이 있는지 확인후 있으면 서비스 아래에 보여주고, 없으면 "No Option" 을 보여줌
        LinearLayout optionAddLn = (LinearLayout) convertView.findViewById(R.id.mainSaleCartListLinearLayout2);

        TextView tvCategoryColor2 = (TextView)convertView.findViewById(R.id.mainSaleCartListCategoryColor2);
        TextView tvOptionAdd = (TextView) convertView.findViewById(R.id.mainSaleCartListOptionAdditionalTv);
        TextView tvEach2 = (TextView) convertView.findViewById(R.id.mainSaleCartListEach2);

        if (!GlobalMemberValues.isStrEmpty(mArSrc.get(position).mCategoryColor)) {
            tvCategoryColor2.setBackgroundColor(Color.parseColor(mArSrc.get(position).mCategoryColor));
        } else {
            tvCategoryColor2.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        String tempModifierCode = mArSrc.get(position).modifiercode;
        String tempModifierIdx = mArSrc.get(position).modifieridx;
        String tempOptionTxt = mArSrc.get(position).optionTxt;
        String tempAdditionalTxt1 = mArSrc.get(position).additionalTxt1;
        String tempAdditionalTxt2 = mArSrc.get(position).additionalTxt2;

        // Food 항목이 아닌 Product, Giftcard, Quick Sale 등일 경우
        if (iconImg > 0 || mArSrc.get(position).mQuickSaleYN.equals("Y") || mArSrc.get(position).mQuickSaleYN == "Y") {
            tempOptionTxt = "";
            tempAdditionalTxt1 = "";
            tempAdditionalTxt2 = "";
        }

        tvEach2.setText("");

        if (!GlobalMemberValues.isStrEmpty(tempOptionTxt) || !GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)
                || !GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
            if (tempOptionTxt.contains("-J-")){
                tempOptionTxt = tempOptionTxt.replace("-J-","\n");
            }
            if (tempAdditionalTxt1.contains("-J-")){
                tempAdditionalTxt1 = tempAdditionalTxt1.replace("-J-","\n");
            }
            if (tempAdditionalTxt2.contains("-J-")){
                tempAdditionalTxt2 = tempAdditionalTxt2.replace("-J-","\n");
            }
            optionAddLn.setVisibility(View.VISIBLE);
            if (tempOptionTxt.contains("-J-")){
                tempOptionTxt = tempOptionTxt.replace("-J-","\n");
            }
            if (tempAdditionalTxt1.contains("-J-")){
                tempAdditionalTxt1 = tempAdditionalTxt1.replace("-J-","\n");
            }
            if (tempAdditionalTxt2.contains("-J-")){
                tempAdditionalTxt2 = tempAdditionalTxt2.replace("-J-","\n");
            }

            String tempOptionAddTxt = "";
            if (!GlobalMemberValues.isStrEmpty(tempOptionTxt)) {
                //tempOptionAddTxt = "[Option] "+ tempOptionTxt;
                tempOptionAddTxt = ""+ tempOptionTxt;
            }
            if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt1)) {
                if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                    tempOptionAddTxt += "\n[Add Ingredients]\n" + tempAdditionalTxt1;
                } else {
                    tempOptionAddTxt = "[Add Ingredients]\n" + tempAdditionalTxt1;
                }
            }
            if (!GlobalMemberValues.isStrEmpty(tempAdditionalTxt2)) {
                if (!GlobalMemberValues.isStrEmpty(tempOptionAddTxt)) {
                    tempOptionAddTxt += "\n\n[General Modifier]\n" + tempAdditionalTxt2;
                } else {
                    tempOptionAddTxt = "[General Modifier]\n" + tempAdditionalTxt2;
                }
            }

            tempOptionAddTxt = GlobalMemberValues.getReplaceText(tempOptionAddTxt, ", ", "\n");
            tempOptionAddTxt = GlobalMemberValues.getReplaceText(tempOptionAddTxt, "($", "  ($");

            // [General Modifier] 텍스트 색상 변경 ---------------------------------------------------------------------
            String tempContents = tempOptionAddTxt;
            SpannableString spannableString = new SpannableString(tempContents);

            String word = "[General Modifier]";
            int start = tempContents.indexOf(word);
            int end = start + word.length();

            if (GlobalMemberValues.isStrEmpty(spannableString.toString())) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3acc5e")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
//            spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            // ------------------------------------------------------------------------------------------------------


            tvOptionAdd.setText("");
            tvOptionAdd.setTextSize(tempFontSize2 + 2);
            tvOptionAdd.append(spannableString);
            tvOptionAdd.setTextColor(Color.parseColor(itemTextColor));

            tvEach2.setText("");
            tvEach2.setTextSize(tempFontSize2);
            tvEach2.setTextColor(Color.parseColor("#36bdff"));

            int qtyInt = 1;
            if (!GlobalMemberValues.isStrEmpty(mArSrc.get(position).mSQty)) {
                qtyInt = GlobalMemberValues.getIntAtString(mArSrc.get(position).mSQty);
            }

            // 옵션+추가사항 추가된 금액
            double tvOptionAdditionalPrice = 0;
            tvOptionAdditionalPrice = GlobalMemberValues.getDoubleAtString(mArSrc.get(position).optionprice) +
                    GlobalMemberValues.getDoubleAtString(mArSrc.get(position).additionalprice1) +
                    GlobalMemberValues.getDoubleAtString(mArSrc.get(position).additionalprice2);

            tvOptionAdditionalPrice = tvOptionAdditionalPrice * qtyInt;
            tvOptionAdditionalPrice = tvOptionAdditionalPrice;

            String tvOptionAdditionalPriceStr = "0";
            if (tvOptionAdditionalPrice == 0) {
                tvOptionAdditionalPriceStr = "0";
            } else {
                if (tvOptionAdditionalPrice > 0) {
                    tvOptionAdditionalPriceStr = "+" + GlobalMemberValues.getCommaStringForDouble(tvOptionAdditionalPrice + "");
                } else {
                    tvOptionAdditionalPriceStr = "-" + GlobalMemberValues.getCommaStringForDouble(tvOptionAdditionalPrice + "");
                }
            }

            tvEach2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvEach2.setText("(" + tvOptionAdditionalPriceStr + ")");


        } else {
//            int optionCount = GlobalMemberValues.getIntAtString(
//                    MssqlDatabase.getResultSetValueToString("select count(idx) from salon_storeservice_option where svcidx = " + tempSvcIdx));
//            int additionalCount = GlobalMemberValues.getIntAtString(
//                    MssqlDatabase.getResultSetValueToString("select count(idx) from salon_storeservice_additional where svcidx = " + tempSvcIdx));
            int optionCount = GlobalMemberValues.getIntAtString(
                    dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option where svcidx = '" + tempSvcIdx + "' "));
            int additionalCount = GlobalMemberValues.getIntAtString(
                    dbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_additional where svcidx = '" + tempSvcIdx + "' "));
            if (optionCount > 0 || additionalCount > 0) {
                optionAddLn.setVisibility(View.VISIBLE);
            } else {
                optionAddLn.setVisibility(View.GONE);
            }



            tvOptionAdd.setText("");
            tvOptionAdd.setTextSize(12);
            //tvOptionAdd.append("No Option or Add Ingredients");

            tvEach2.setText("");
            tvEach2.setTextSize(12);
            //tvEach2.setTextColor(Color.parseColor(itemTextColor));
        }


        TextView tvModifierEdit = (TextView)convertView.findViewById(R.id.modifieredit);
        //tvModifierEdit.setVisibility(View.GONE);
        tvModifierEdit.setTag(tempModifierCode);
        if (GlobalMemberValues.mDevicePAX) {
            tvModifierEdit.setPadding(4, 5, 4, 5);
            tvModifierEdit.setTextSize(12);
        }

        tvModifierEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainMiddleService.checkAllDiscountExtra()) return;
                // Pay 버튼 클릭여부 체크하여 클릭했을 경우 기능 못하게 ----------------
                if (GlobalMemberValues.GLOBAL_PAYBUTTONCLICKED == "Y") return;
                // ------------------------------------------------------------
                //GlobalMemberValues.displayDialog(MainActivity.mContext, "", v.getTag().toString(), "Close");
                //GlobalMemberValues.logWrite("MainMiddleServiceModifierJ", "선택된 position : " + pos + "\n");
                MainMiddleService.openEditModifier(tempSvcIdx, v.getTag().toString(), pos, temporarySaleCart);
            }
        });

        int optionCount = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_option where svcidx = '" + tempSvcIdx + "' "));
        int additionalCount = GlobalMemberValues.getIntAtString(
                MainActivity.mDbInit.dbExecuteReadReturnString("select count(idx) from salon_storeservice_additional where svcidx = '" + tempSvcIdx + "' "));

        if (optionCount > 0 || additionalCount > 0) {
            tvModifierEdit.setVisibility(View.VISIBLE);
        } else {
            tvModifierEdit.setVisibility(View.GONE);
        }

        /********************************************************************************************************/

        /** Kitchen Memo **************************************************************************************/
        TextView tvCategoryColor3 = (TextView)convertView.findViewById(R.id.mainSaleCartListCategoryColor3);
        TextView tvCategoryColor4 = (TextView)convertView.findViewById(R.id.mainSaleCartListCategoryColor4);

        TextView tvKitchenMemo = (TextView) convertView.findViewById(R.id.mainSaleCartListKitchenMemoTv);
        tvKitchenMemo.setTextSize(tempFontSize1);
        tvKitchenMemo.setTextColor(Color.parseColor("#36bdff"));

        TextView specialrequestTv = (TextView) convertView.findViewById(R.id.specialrequestTv);
        specialrequestTv.setTextSize(tempFontSize2);
        if (GlobalMemberValues.mDeviceEloYN == "N" || GlobalMemberValues.mDeviceEloYN.equals("N")) {
            specialrequestTv.setText("SPECIAL REQ.");
        }
        specialrequestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 주방에 보낼 메모창 띄우기
                LogsSave.saveLogsInDB(111);
                MainMiddleService.openKitchenMemoPopupOnItem(position);
            }
        });


        if (!GlobalMemberValues.isStrEmpty(mArSrc.get(position).mCategoryColor)) {
            tvCategoryColor3.setBackgroundColor(Color.parseColor(mArSrc.get(position).mCategoryColor));
            tvCategoryColor4.setBackgroundColor(Color.parseColor(mArSrc.get(position).mCategoryColor));
        } else {
            tvCategoryColor3.setBackgroundColor(Color.parseColor("#ffffff"));
            tvCategoryColor4.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        String kitchenMemo = mArSrc.get(position).kitchenMemo;
        if (!GlobalMemberValues.isStrEmpty(kitchenMemo)) {
            //kitchenMemoLn.setVisibility(View.VISIBLE);

            tvKitchenMemo.setText(kitchenMemo);
        } else {
            //kitchenMemoLn.setVisibility(View.GONE);

            tvKitchenMemo.setText("");
        }


        TextView tv_kitchenPrintedyn = convertView.findViewById(R.id.mainSaleCartListPrintedYN_Tv);

        if (GlobalMemberValues.getSaleCartKitchenPrintedYN(mArSrc.get(position).tempSaleCartIdx).equals("Y")) {
            tempKitchenPrinted = "Printed on kitchen";
            tempTextColor = "#36bdff";
            tv_kitchenPrintedyn.setBackgroundResource(R.drawable.ab_roundlayout_kitchen_printer);
        } else {
            tempKitchenPrinted = "Not printed on kitchen";
            tempTextColor = "#00ff7f";
            tv_kitchenPrintedyn.setBackgroundResource(R.drawable.ab_roundlayout_not_kitchen_printer);
        }

        tv_kitchenPrintedyn.setText(tempKitchenPrinted);
        tv_kitchenPrintedyn.setTextColor(Color.parseColor(tempTextColor));


        if (mArSrc.get(position).mSaveType == "8" || mArSrc.get(position).mSaveType == "9"){
            kitchenprintingmsgLn.setVisibility(View.GONE);
            tv_kitchenPrintedyn.setVisibility(View.GONE);
        } else {
            tv_kitchenPrintedyn.setVisibility(View.VISIBLE);
            kitchenprintingmsgLn.setVisibility(View.VISIBLE);
            TextView mainSaleCartList_orderkind_Tv = convertView.findViewById(R.id.mainSaleCartList_orderkind_Tv);
            mainSaleCartList_orderkind_Tv.setText(mArSrc.get(position).mServiceType);
            mainSaleCartList_orderkind_Tv.setTextColor(Color.parseColor("#FFFFFF"));
            mainSaleCartList_orderkind_Tv.setTextSize(tempFontSize2);
            switch (mArSrc.get(position).mServiceType){
                case "TO GO" :
                    mainSaleCartList_orderkind_Tv.setBackgroundColor(Color.parseColor("#189dba"));
                    break;
                case "DELIVERY" :
                    mainSaleCartList_orderkind_Tv.setBackgroundColor(Color.parseColor("#11cc40"));
                    break;
                case "HERE" :
                    mainSaleCartList_orderkind_Tv.setBackgroundColor(Color.parseColor("#d62966"));
                    break;
            }
        }

        /********************************************************************************************************/


        if (!(itemSaveType == "8" || itemSaveType == "9")) {
            if (GlobalMemberValues.isMultiCheckOnCart()) {
                // 멀티선택관련 추가 ------------------------------------------------------------
                // CheckBox는 기본적으로 이벤트를 가지고 있기 때문에 ListView의 아이템
                // 클릭리즈너를 사용하기 위해서는 CheckBox의 이벤트를 없애 주어야 한다.
                GlobalMemberValues.logWrite("MultiTestLog", "position : " + position + "\n");

                if (viewHolder != null && viewHolder.cBox != null) {
                    viewHolder.cBox.setClickable(false);
                    viewHolder.cBox.setFocusable(false);
                    // isCheckedConfrim 배열은 초기화시 모두 false로 초기화 되기때문에
                    // 기본적으로 false로 초기화 시킬 수 있다.
                    if (MainMiddleService.isCheckedConfrim.length > position) {
                        viewHolder.cBox.setChecked(MainMiddleService.isCheckedConfrim[position]);

                        // 이곳에 백그라운드 적용
                        if (MainMiddleService.isCheckedConfrim[position]){
                            // check in
                            viewHolder.ln_background.setBackgroundColor(Color.parseColor("#32323e"));
                        } else {
                            // check out
                            viewHolder.ln_background.setBackgroundColor(Color.TRANSPARENT);
                        }
                    } else {

                    }
                }
                // ----------------------------------------------------------------------------
            }
        }

        if (mArSrc.get(position).mSvcName.equals(GlobalMemberValues.mCommonGratuityName)) {
            if (kitchenMemoLn != null) {
                kitchenMemoLn.setVisibility(View.GONE);
            }
            tv_kitchenPrintedyn.setVisibility(View.GONE);
            kitchenprintingmsgLn.setVisibility(View.GONE);
            tvQty.setVisibility(View.GONE);
            tvEach.setVisibility(View.GONE);

            CheckBox checkBoxView = (CheckBox)convertView.findViewById(R.id.mainSaleCartListCheckBox);
            checkBoxView.setVisibility(View.VISIBLE);

            // Common Gratuity 일 경우 해당 라인전체를 안보이게한다.
            if (GlobalMemberValues.isDeleteGratuity()){
                // Gratuity 보임.
            } else {
                // Gratuity 안보임.
                viewHolder.ln_background.setVisibility(View.GONE);
            }

        } else {
            // Common Gratuity 가 아닐 경우 해당 라인전체를 다시 보이게한다.
            if (viewHolder != null){
                if (viewHolder.ln_background != null) {
                    viewHolder.ln_background.setVisibility(View.VISIBLE);
                }
            }
        }

        GlobalMemberValues.logWrite("POSITION VALUE", "position : " + position + "\n");

//        TextView b_show_dollar = (TextView)convertView.findViewById(R.id.mainSaleCart_dollar);
//        if (GlobalMemberValues.ISDISPLAYPRICEDOLLAR){
//            b_show_dollar.setVisibility(View.VISIBLE);
//        } else {
//            b_show_dollar.setVisibility(View.INVISIBLE);
//        }

        /**
         //LinearLayout lnDiscount = (LinearLayout)convertView.findViewById(R.id.mainSaleCartDiscountLinearLayout);
         //lnDiscount.setVisibility(View.VISIBLE);

         TextView tvDiscountContents = (TextView)convertView.findViewById(R.id.mainSaleCartDiscountContents);
         tvDiscountContents.setTextColor(Color.parseColor(GlobalMemberValues.STR_MAINSALECART_TEXTCOLOR_DISCOUNT));
         //tvDiscountContents.setText("TEST DISCOUNT");
         TextView tvDiscountPrice = (TextView)convertView.findViewById(R.id.mainSaleCartDiscountContents);
         tvDiscountContents.setTextColor(Color.parseColor(GlobalMemberValues.STR_MAINSALECART_TEXTCOLOR_DISCOUNT));
         **/

        GlobalMemberValues.logWrite("LVListViewIdx", "idx : " + mArSrc.get(position).tempSaleCartIdx);

        if (tempSvcName.contains("==========")) {
            tvMenuName.setText("=====================================");
            tvMenuName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tvQty.setVisibility(View.GONE);
            tvEach.setVisibility(View.GONE);
            optionAddLn.setVisibility(View.GONE);
            kitchenMemoLn.setVisibility(View.GONE);
            kitchenprintingmsgLn.setVisibility(View.GONE);
//            CheckBox checkBoxView = (CheckBox)convertView.findViewById(R.id.mainSaleCartListCheckBox);
//            checkBoxView.setVisibility(View.INVISIBLE);
        } else {
            tvQty.setVisibility(View.VISIBLE);
            tvEach.setVisibility(View.VISIBLE);
            optionAddLn.setVisibility(View.VISIBLE);
            kitchenMemoLn.setVisibility(View.VISIBLE);
            kitchenprintingmsgLn.setVisibility(View.VISIBLE);
        }

        // 07.18.2022 - add pay for cash, card
        if (tempChkBox != null && !GlobalMemberValues.getAddPayType().equals("A")) {
            String tempAddPayName = "";
            if (GlobalMemberValues.getAddPayData() != null && GlobalMemberValues.getAddPayData().length == 4) {
                tempAddPayName = GlobalMemberValues.getAddPayData()[2];
            }
            if (!GlobalMemberValues.isStrEmpty(tempAddPayName)) {
                String tempSvcName2 = mArSrc.get(position).mSvcName.toUpperCase();
                if (tempAddPayName.toUpperCase().equals(tempSvcName2.toUpperCase())) {
                    tempChkBox.setVisibility(View.INVISIBLE);
                    kitchenMemoLn.setVisibility(View.GONE);
                    kitchenprintingmsgLn.setVisibility(View.GONE);

                    tvMenuName.setTextColor(Color.parseColor("#48ca2d"));

                    tvQty.setText("-");
                    tvQty.setTextColor(Color.parseColor("#48ca2d"));
                }
            }
        }

//        if (mArSrc.get(position).mSvcName.equals(GlobalMemberValues.mCommonGratuityName) ){
//            convertView.setVisibility(View.GONE);
////            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
////            layoutParams.height = 1;
////            convertView.setLayoutParams(layoutParams);
//        } else {
//            convertView.setVisibility(View.VISIBLE);
//        }

//        if (GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW != null && MainMiddleService.mSaleCartAdapter.getCount() > 1) {
//            GlobalMemberValues.GLOBAL_LAYOUTMEMBER_MAINLISTVIEW.setSelection(MainMiddleService.mSaleCartAdapter.getCount() - 2);
//        }

        // 12292022
        ProductList.setBarcodeQRCodeScan();


        if (GlobalMemberValues.is_customerMain){

            kitchenMemoLn.setVisibility(View.GONE);
            kitchenprintingmsgLn.setVisibility(View.GONE);
            tvQty.setBackgroundResource(R.color.zxing_transparent);
            tvEach.setTextColor(Color.parseColor("#ffffff"));
            tvEach2.setTextColor(Color.parseColor("#a0a0a0"));
            if (GlobalMemberValues.is_myorderOpen){
                CheckBox checkBoxView = (CheckBox)convertView.findViewById(R.id.mainSaleCartListCheckBox);
                checkBoxView.setVisibility(View.GONE);
                ImageButton salecart_customer_minus = (ImageButton) convertView.findViewById(R.id.salecart_customer_minus);
                salecart_customer_minus.setVisibility(View.GONE);
                ImageButton salecart_customer_add = (ImageButton) convertView.findViewById(R.id.salecart_customer_add);
                salecart_customer_add.setVisibility(View.GONE);
            } else {
                ImageButton salecart_customer_minus = (ImageButton) convertView.findViewById(R.id.salecart_customer_minus);
                salecart_customer_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((GlobalMemberValues.getIntAtString(temporarySaleCart.mSQty) - 1) > 0) {
                            String addOne = String.valueOf(GlobalMemberValues.getIntAtString(temporarySaleCart.mSQty) - 1);
                            MainMiddleService.qty_set(addOne,temporarySaleCart,position);
                        }
                    }
                });
                ImageButton salecart_customer_add = (ImageButton) convertView.findViewById(R.id.salecart_customer_add);
                salecart_customer_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addOne = String.valueOf (GlobalMemberValues.getIntAtString(temporarySaleCart.mSQty) + 1);
                        MainMiddleService.qty_set(addOne,temporarySaleCart,position);
                    }
                });
                Button salecart_customer_del = (Button) convertView.findViewById(R.id.salecart_customer_del);
                salecart_customer_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainMiddleService.deleteItemExe(position,false);
                    }
                });

            }

        } else {
            kitchenMemoLn.setVisibility(View.VISIBLE);
            tvQty.setBackgroundResource(R.drawable.roundlayout_background_qty);
            tvEach.setTextColor(Color.parseColor("#36bdff"));
            tvEach2.setTextSize(tempFontSize2);
            tvEach2.setTextColor(Color.parseColor("#36bdff"));
        }


        return convertView;
    }


    // 멀티선택관련 추가
    // 체크박스 저장함수
    public class ViewHolder {
        CheckBox cBox = null;
        LinearLayout ln_background = null;
    }


}

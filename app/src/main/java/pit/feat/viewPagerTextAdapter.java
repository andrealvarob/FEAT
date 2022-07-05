package pit.feat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.w3c.dom.Text;

import pit.feat.utility.SecretTextView;

/**
 * Created by Usuário on 17/05/2016.
 */
public class viewPagerTextAdapter extends PagerAdapter {

    private int dotsCount;
    private ImageView[] dots;
    int globalPosition;

    private int[] image_resources = {R.drawable.user_icon,R.drawable.icon_pimenta, R.drawable.icon_search, R.drawable.icon_key};
    private String[] text_resources = {"Bem-vindo!", "Encontre o seu Feat.", "Descubra e explore seus fetiches", "Caso não saiba qual o seu, ajudaremos a encontrá-lo!"};
    private Context ctx;
    private LayoutInflater layoutinflater;
    private int firstTime;
    private Typeface font;


    public viewPagerTextAdapter(Context context){
        this.ctx = context;
        font = Typeface.createFromAsset(ctx.getAssets(), "CaviarDreams.ttf");

    }

    @Override
    public int getCount() {

        return text_resources.length;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);


    }



    public void updateText(ViewGroup container, int position) {
        layoutinflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutinflater.inflate(R.layout.pager_item, container, false);
        /*
        HTextView textview = (HTextView) item_view.findViewById(R.id.text_viewPager);



        if(position == 0){
            textview.setTextColor(Color.parseColor("#b71c1c"));
            textview.setTextSize(25);
            textview.setAnimateType(HTextViewType.SCALE);
            textview.animateText(text_resources[position]);
            //textview.setText(text_resources[position]);


        }
        else {
            textview.setTextColor(Color.parseColor("#bdbdbd"));
            textview.setTextSize(15);
            textview.setAnimateType(HTextViewType.SCALE);
            textview.animateText(text_resources[position]);
            //textview.setText(text_resources[position]);
        }
        */


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutinflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutinflater.inflate(R.layout.pager_item, container, false);


        ImageView imageview = (ImageView) item_view.findViewById(R.id.image_viewPager);

        //TextView textview = (TextView) item_view.findViewById(R.id.text_viewPager);


        SecretTextView textview = (SecretTextView) item_view.findViewById(R.id.text_viewPager);
        //textview.setWidth(item_view.getWidth());


        //LinearLayout indicator = (LinearLayout) item_view.findViewById(R.id.viewPagerCountDots);
        /*
        ImageView dot1 = (ImageView) item_view.findViewById(R.id.viewPager_dot1);
        ImageView dot2 = (ImageView) item_view.findViewById(R.id.viewPager_dot2);
        ImageView dot3 = (ImageView) item_view.findViewById(R.id.viewPager_dot3);
        ImageView dot4 = (ImageView) item_view.findViewById(R.id.viewPager_dot4);
        */

        imageview.setImageResource(image_resources[position]);
        //textview.setTextSize(12);
        //textview.setAnimateType(HTextViewType.SCALE);

        textview.setTypeface(font);
        //textview.setText("Teste");

        if(position == 0){
            textview.setTextColor(Color.parseColor("#b71c1c"));
            textview.setTextSize(20);
            textview.setText(text_resources[position]);
            textview.show();
            //textview.animateText(text_resources[position]);

        }
        else {
            textview.setTextColor(Color.parseColor("#bdbdbd"));
            textview.setTextSize(12);
            textview.setText(text_resources[position]);
            textview.show();
            //textview.animateText(text_resources[position]);
        }
            //textview.setText(text_resources[position]);
        /*
            if(position == 1){
                dot1.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot2.setImageDrawable(ctx.getDrawable(R.drawable.selecteditem_dot));
                dot3.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot4.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
            }
            else if(position == 2){
                dot1.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot2.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot3.setImageDrawable(ctx.getDrawable(R.drawable.selecteditem_dot));
                dot4.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
            }
            else if(position == 3){
                dot1.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot2.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot3.setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
                dot4.setImageDrawable(ctx.getDrawable(R.drawable.selecteditem_dot));
            }

        }

        for (int i = 0; i < dotsCount; i++) {

            dots[i].setImageDrawable(ctx.getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(ctx.getDrawable(R.drawable.selecteditem_dot));
        */

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

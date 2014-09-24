package edwinotten.philipshue;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import edwinotten.philipshue.controllers.HueCommander;
import edwinotten.philipshue.models.Lamp;
import edwinotten.philipshue.models.LampCollection;


/**
 * A fragment representing a single Lamp detail screen.
 * This fragment is either contained in a {@link LampListActivity}
 * in two-pane mode (on tablets) or a {@link LampDetailActivity}
 * on handsets.
 */
public class LampDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_LAMP = "lamp_data";
    protected SeekBar seekbarHue;
    protected SeekBar seekbarSat;
    protected SeekBar seekbarBri;
    protected TextView currentColor;
    protected TextView colorPreview;
    protected Button buttonSetColor;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LampDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_lamp_detail, container, false);

        this.seekbarHue = (SeekBar) rootView.findViewById(R.id.seekbarHue);
        this.seekbarSat = (SeekBar) rootView.findViewById(R.id.seekbarSat);
        this.seekbarBri = (SeekBar) rootView.findViewById(R.id.seekbarBri);
        this.currentColor = (TextView) rootView.findViewById(R.id.currentColor);
        this.colorPreview = (TextView) rootView.findViewById(R.id.newColor);
        this.buttonSetColor = (Button) rootView.findViewById(R.id.buttonNewColor);

        String lampId = getActivity().getIntent().getStringExtra(this.ARG_ITEM_ID);
        LampCollection lampCollection = LampCollection.getInstance();
        Lamp lamp = lampCollection.lamps.get(new Integer(Integer.parseInt(lampId)));

        TextView title = (TextView) rootView.findViewById(R.id.detailTitle);
        title.setText(lamp.name);
        this.seekbarHue.setProgress(lamp.state.hue);
        this.seekbarSat.setProgress(lamp.state.sat);
        this.seekbarBri.setProgress(lamp.state.bri);

        float[] hsv = new float[3];
        hsv[0] = (float) lamp.state.hue / 65535F * 360F;
        hsv[1] = (float) lamp.state.sat / 255F * 1F;
        hsv[2] = (float) lamp.state.bri / 255F * 1F;
        this.currentColor.setTextColor(Color.HSVToColor(hsv));

        this.buttonSetColor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(v);
            }
        });

        try {
            //Listener to receive changes to the seekbarHue's progress level
            seekbarHue
                    .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            updateColorPreview();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        public void onStopTrackingTouch(SeekBar arg0) {
                            updateColorPreview();
                            setColor(rootView);
                        }
                    });

            //Listener to receive changes to the seekbarSat's progress level
            seekbarSat
                    .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            updateColorPreview();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        public void onStopTrackingTouch(SeekBar arg0) {
                            updateColorPreview();
                            setColor(rootView);
                        }
                    });

            //Listener to receive changes to the seekbarBri's progress level
            seekbarBri
                    .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            updateColorPreview();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        public void onStopTrackingTouch(SeekBar arg0) {
                            updateColorPreview();
                            setColor(rootView);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }


    public void setColor(View view) {
        String lampId = getActivity().getIntent().getStringExtra(LampDetailFragment.ARG_ITEM_ID);
        int[] philipsColors = getPhilipsColors();
        HueCommander async = new HueCommander("/lights/" + lampId + "/state", "{\"on\":true,\"hue\":" + Integer.toString(philipsColors[0]) + ",\"sat\":" + Integer.toString(philipsColors[1]) + ",\"bri\":" + Integer.toString(philipsColors[2]) + ",\"transitiontime\":0}");
        async.execute();
    }

    public int[] getPhilipsColors() {
        int[] hsv = new int[3];
        hsv[0] = Math.round(((float) this.seekbarHue.getProgress()) / this.seekbarHue.getMax() * 65535);
        hsv[1] = Math.round(((float) this.seekbarSat.getProgress()) / this.seekbarSat.getMax() * 255);
        hsv[2] = Math.round(((float) this.seekbarBri.getProgress()) / this.seekbarBri.getMax() * 255);
        return hsv;
    }

    public void updateColorPreview() {
        float[] hsv = new float[3];
        hsv[0] = ((float) this.seekbarHue.getProgress()) / this.seekbarHue.getMax() * 360;
        hsv[1] = ((float) this.seekbarSat.getProgress()) / this.seekbarSat.getMax() * 1;
        hsv[2] = ((float) this.seekbarBri.getProgress()) / this.seekbarBri.getMax() * 1;
        this.colorPreview.setBackgroundColor(Color.HSVToColor(hsv));
    }
}
